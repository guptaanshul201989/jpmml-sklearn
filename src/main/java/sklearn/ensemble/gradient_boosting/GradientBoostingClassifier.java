/*
 * Copyright (c) 2015 Villu Ruusmann
 *
 * This file is part of JPMML-SkLearn
 *
 * JPMML-SkLearn is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPMML-SkLearn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with JPMML-SkLearn.  If not, see <http://www.gnu.org/licenses/>.
 */
package sklearn.ensemble.gradient_boosting;

import java.util.ArrayList;
import java.util.List;

import org.dmg.pmml.DataType;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.OpType;
import org.dmg.pmml.mining.MiningModel;
import org.dmg.pmml.regression.RegressionModel;
import org.jpmml.converter.CMatrixUtil;
import org.jpmml.converter.CategoricalLabel;
import org.jpmml.converter.ModelUtil;
import org.jpmml.converter.Schema;
import org.jpmml.converter.SchemaUtil;
import org.jpmml.converter.mining.MiningModelUtil;
import sklearn.Classifier;
import sklearn.Estimator;
import sklearn.HasEstimatorEnsemble;
import sklearn.HasPriorProbability;
import sklearn.tree.HasTreeOptions;
import sklearn.tree.TreeRegressor;
import sklearn2pmml.EstimatorProxy;

public class GradientBoostingClassifier extends Classifier implements HasEstimatorEnsemble<TreeRegressor>, HasTreeOptions {

	public GradientBoostingClassifier(String module, String name){
		super(module, name);
	}

	@Override
	public int getNumberOfFeatures(){

		// SkLearn 0.18
		if(containsKey("n_features")){
			return getInteger("n_features");
		}

		// SkLearn 0.19+
		return super.getNumberOfFeatures();
	}

	@Override
	public DataType getDataType(){
		return DataType.FLOAT;
	}

	@Override
	public MiningModel encodeModel(Schema schema){
		LossFunction loss = getLoss();

		int numberOfClasses = loss.getK();

		HasPriorProbability init = getInit();

		Number learningRate = getLearningRate();

		Schema segmentSchema = schema.toAnonymousRegressorSchema(DataType.DOUBLE);

		CategoricalLabel categoricalLabel = (CategoricalLabel)schema.getLabel();

		if(numberOfClasses == 1){
			SchemaUtil.checkSize(2, categoricalLabel);

			List<? extends Number> initRawPrediction = loss.computeInitRawPrediction(init);

			MiningModel miningModel = GradientBoostingUtil.encodeGradientBoosting(this, initRawPrediction.get(0), learningRate, segmentSchema)
				.setOutput(ModelUtil.createPredictedOutput(FieldName.create("decisionFunction(" + categoricalLabel.getValue(1) + ")"), OpType.CONTINUOUS, DataType.DOUBLE, loss.createTransformation()));

			return MiningModelUtil.createBinaryLogisticClassification(miningModel, 1d, 0d, RegressionModel.NormalizationMethod.NONE, true, schema);
		} else

		if(numberOfClasses >= 3){
			SchemaUtil.checkSize(numberOfClasses, categoricalLabel);

			List<? extends Number> initRawPrediction = loss.computeInitRawPrediction(init);

			List<? extends TreeRegressor> estimators = getEstimators();

			List<MiningModel> miningModels = new ArrayList<>();

			for(int i = 0, columns = categoricalLabel.size(), rows = (estimators.size() / columns); i < columns; i++){
				List<? extends TreeRegressor> columnEstimators = CMatrixUtil.getColumn(estimators, rows, columns, i);

				GradientBoostingClassifierProxy estimatorProxy = new GradientBoostingClassifierProxy(){

					@Override
					public List<? extends TreeRegressor> getEstimators(){
						return columnEstimators;
					}
				};

				MiningModel miningModel = GradientBoostingUtil.encodeGradientBoosting(estimatorProxy, initRawPrediction.get(i), learningRate, segmentSchema)
					.setOutput(ModelUtil.createPredictedOutput(FieldName.create("decisionFunction(" + categoricalLabel.getValue(i) + ")"), OpType.CONTINUOUS, DataType.DOUBLE, loss.createTransformation()));

				miningModels.add(miningModel);
			}

			return MiningModelUtil.createClassification(miningModels, RegressionModel.NormalizationMethod.SIMPLEMAX, true, schema);
		} else

		{
			throw new IllegalArgumentException();
		}
	}

	public LossFunction getLoss(){
		return get("loss_", LossFunction.class);
	}

	public HasPriorProbability getInit(){
		return get("init_", HasPriorProbability.class);
	}

	public Number getLearningRate(){
		return getNumber("learning_rate");
	}

	@Override
	public List<? extends TreeRegressor> getEstimators(){
		return getArray("estimators_", TreeRegressor.class);
	}

	abstract
	private class GradientBoostingClassifierProxy extends EstimatorProxy implements HasEstimatorEnsemble<TreeRegressor>, HasTreeOptions {

		@Override
		public Estimator getEstimator(){
			return GradientBoostingClassifier.this;
		}
	}
}
