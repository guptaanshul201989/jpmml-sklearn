/*
 * Copyright (c) 2017 Villu Ruusmann
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
package tpot.builtins;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.dmg.pmml.DataType;
import org.dmg.pmml.Expression;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.OpType;
import org.dmg.pmml.Output;
import org.dmg.pmml.OutputField;
import org.jpmml.converter.CategoricalFeature;
import org.jpmml.converter.PMMLEncoder;

public class CategoricalOutputFeature extends CategoricalFeature {

	private Output output = null;


	public CategoricalOutputFeature(PMMLEncoder encoder, Output output, OutputField outputField, List<?> values){
		this(encoder, output, outputField.getName(), outputField.getDataType(), values);
	}

	public CategoricalOutputFeature(PMMLEncoder encoder, Output output, FieldName name, DataType dataType, List<?> values){
		super(encoder, name, dataType, values);

		setOutput(output);
	}

	@Override
	public ContinuousOutputFeature toContinuousFeature(){
		PMMLEncoder encoder = ensureEncoder();

		Output output = getOutput();

		OutputField outputField = getField();

		DataType dataType = outputField.getDataType();
		switch(dataType){
			case INTEGER:
			case FLOAT:
			case DOUBLE:
				break;
			default:
				throw new IllegalArgumentException();
		}

		outputField.setOpType(OpType.CONTINUOUS);

		return new ContinuousOutputFeature(encoder, output, outputField);
	}

	@Override
	public ContinuousOutputFeature toContinuousFeature(DataType dataType){
		return (ContinuousOutputFeature)super.toContinuousFeature(dataType);
	}

	@Override
	protected ContinuousOutputFeature toContinuousFeature(FieldName name, DataType dataType, Supplier<? extends Expression> expressionSupplier){
		return (ContinuousOutputFeature)super.toContinuousFeature(name, dataType, expressionSupplier);
	}

	@Override
	public OutputField getField(){
		Output output = getOutput();

		FieldName name = getName();

		OutputField outputField = OutputUtil.getOutputField(output, name);
		if(outputField == null){
			throw new IllegalArgumentException(name.getValue());
		}

		return outputField;
	}

	public Output getOutput(){
		return this.output;
	}

	private void setOutput(Output output){
		this.output = Objects.requireNonNull(output);
	}
}
