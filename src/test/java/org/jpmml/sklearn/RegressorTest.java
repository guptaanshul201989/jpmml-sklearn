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
package org.jpmml.sklearn;

import org.dmg.pmml.FieldName;
import org.jpmml.evaluator.FloatEquivalence;
import org.jpmml.evaluator.PMMLEquivalence;
import org.jpmml.evaluator.RealNumberEquivalence;
import org.junit.Test;

public class RegressorTest extends EstimatorTest {

	@Test
	public void evaluateAdaBoostAuto() throws Exception {
		evaluate("AdaBoost", "Auto");
	}

	@Test
	public void evaluateBayesianARDAuto() throws Exception {
		evaluate("BayesianARD", "Auto");
	}

	@Test
	public void evaluateBayesianRidgeAuto() throws Exception {
		evaluate("BayesianRidge", "Auto");
	}

	@Test
	public void evaluateDecisionTreeAuto() throws Exception {
		evaluate("DecisionTree", "Auto");
	}

	@Test
	public void evaluateDecisionTreeAutoNA() throws Exception {
		FieldName[] transformFields = {FieldName.create("eval(nodeId)")};

		evaluate("DecisionTree", "AutoNA", excludeFields(transformFields));
	}

	@Test
	public void evaluateDecisionTreeEnsembleAuto() throws Exception {
		evaluate("DecisionTreeEnsemble", "Auto");
	}

	@Test
	public void evaluateTransformedDecisionTreeAuto() throws Exception {
		evaluate("TransformedDecisionTree", "Auto");
	}

	@Test
	public void evaluateDummyAuto() throws Exception {
		evaluate("Dummy", "Auto");
	}

	@Test
	public void evaluateElasticNetAuto() throws Exception {
		evaluate("ElasticNet", "Auto");
	}

	@Test
	public void evaluateExtraTreesAuto() throws Exception {
		evaluate("ExtraTrees", "Auto");
	}

	@Test
	public void evaluateGradientBoostingAuto() throws Exception {
		evaluate("GradientBoosting", "Auto");
	}

	@Test
	public void evaluateHuberAuto() throws Exception {
		evaluate("Huber", "Auto");
	}

	@Test
	public void evaluateLarsAuto() throws Exception {
		evaluate("Lars", "Auto");
	}

	@Test
	public void evaluateLassoAuto() throws Exception {
		evaluate("Lasso", "Auto");
	}

	@Test
	public void evaluateLassoLarsAuto() throws Exception {
		evaluate("LassoLars", "Auto");
	}

	@Test
	public void evaluateLGBMAuto() throws Exception {
		evaluate("LGBM", "Auto", new RealNumberEquivalence(1));
	}

	@Test
	public void evaluateLinearRegressionAuto() throws Exception {
		evaluate("LinearRegression", "Auto");
	}

	@Test
	public void evaluateLinearRegressionAutoNA() throws Exception {
		FieldName[] transformFields = {FieldName.create("predict(mpg)"), FieldName.create("cut(predict(mpg))")};

		evaluate("LinearRegression", "AutoNA", excludeFields(transformFields));
	}

	@Test
	public void evaluateLinearRegressionEnsembleAuto() throws Exception {
		evaluate("LinearRegressionEnsemble", "Auto");
	}

	@Test
	public void evaluateTransformedLinearRegressionAuto() throws Exception {
		evaluate("TransformedLinearRegression", "Auto");
	}

	@Test
	public void evaluateOMPAuto() throws Exception {
		evaluate("OMP", "Auto");
	}

	@Test
	public void evaluateRandomForestAuto() throws Exception {
		evaluate("RandomForest", "Auto");
	}

	@Test
	public void evaluateRidgeAuto() throws Exception {
		evaluate("Ridge", "Auto");
	}

	@Test
	public void evaluateTheilSenAuto() throws Exception {
		evaluate("TheilSen", "Auto");
	}

	@Test
	public void evaluateTPOTAuto() throws Exception {
		evaluate("TPOT", "Auto");
	}

	@Test
	public void evaluateVotingEnsembleAuto() throws Exception {
		evaluate("VotingEnsemble", "Auto");
	}

	@Test
	public void evaluateXGBAuto() throws Exception {
		evaluate("XGB", "Auto", new FloatEquivalence(1));
	}

	@Test
	public void evaluateAdaBoostHousing() throws Exception {
		evaluate("AdaBoost", "Housing");
	}

	@Test
	public void evaluateBayesianRidgeHousing() throws Exception {
		evaluate("BayesianRidge", "Housing");
	}

	@Test
	public void evaluateIsolationForestHousing() throws Exception {
		evaluate("IsolationForest", "Housing", new PMMLEquivalence(5e-12, 5e-12));
	}

	@Test
	public void evaluateKNNHousing() throws Exception {
		evaluate("KNN", "Housing");
	}

	@Test
	public void evaluateMLPHousing() throws Exception {
		evaluate("MLP", "Housing");
	}

	@Test
	public void evaluateOneClassSVMHousing() throws Exception {
		evaluate("OneClassSVM", "Housing");
	}

	@Test
	public void evaluateSGDHousing() throws Exception {
		evaluate("SGD", "Housing");
	}

	@Test
	public void evaluateSVRHousing() throws Exception {
		evaluate("SVR", "Housing");
	}

	@Test
	public void evaluateLinearSVRHousing() throws Exception {
		evaluate("LinearSVR", "Housing");
	}

	@Test
	public void evaluateNuSVRHousing() throws Exception {
		evaluate("NuSVR", "Housing");
	}

	@Test
	public void evaluateTPOTHousing() throws Exception {
		evaluate("TPOT", "Housing");
	}

	@Test
	public void evaluateVotingEnsembleHousing() throws Exception {
		evaluate("VotingEnsemble", "Housing");
	}
}