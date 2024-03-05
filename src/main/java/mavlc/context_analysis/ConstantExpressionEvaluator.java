/*******************************************************************************
 * Copyright (c) 2016-2019 Embedded Systems and Applications Group
 * Department of Computer Science, Technische Universitaet Darmstadt,
 * Hochschulstr. 10, 64289 Darmstadt, Germany.
 *
 * All rights reserved.
 *
 * This software is provided free for educational use only.
 * It may not be used for commercial purposes without the
 * prior written permission of the authors.
 ******************************************************************************/
package mavlc.context_analysis;

import mavlc.errors.NonConstantExpressionError;
import mavlc.syntax.AstNode;
import mavlc.syntax.AstNodeBaseVisitor;
import mavlc.syntax.expression.*;

/* TODO enter group information
 *
 * EiCB group number: 57
 * Names and matriculation numbers of all group members:
 * Stefan Nikolaus Dobrea MatrNr. : 2802837
 * Franck Boudouin Tameze MatrNr. : 2682002
 * Narges Ahmadi Asl MatNr. : 2732428
 */

public class ConstantExpressionEvaluator extends AstNodeBaseVisitor<Integer, Void> {
	@Override
	protected Integer defaultOperation(AstNode node, Void obj) {
		if(node instanceof Expression) {
			throw new NonConstantExpressionError((Expression) node);
		} else {
			throw new RuntimeException("Internal compiler error: should not try to constant-evaluate non-expressions");
		}
	}

	@Override
	public Integer visitIntValue(IntValue intValue, Void __) {
		return intValue.value;
	}

	// DONE implement (exercise 2.3)

	@Override
	public Integer visitUnaryMinus(UnaryMinus unaryMinus, Void __) {
		return Math.negateExact(unaryMinus.operand.accept(this));
	}
	@Override
	public Integer visitExponentiation(Exponentiation exponentiation, Void __) {
		Integer base = exponentiation.leftOperand.accept(this);
		Integer exponent = exponentiation.rightOperand.accept(this);
		if(exponent < 0 ){
			throw new ArithmeticException();
		}
		int result = 1;
		for(int i = 0; i < exponent; ++i) {
			result = Math.multiplyExact(result, base);
		}
		return result;
	}
	@Override
	public Integer visitMultiplication(Multiplication multiplication, Void __) {
		Integer leftOp = multiplication.leftOperand.accept(this);
		Integer rightOp = multiplication.rightOperand.accept(this);

		return Math.multiplyExact(leftOp, rightOp);
	}
	@Override
	public Integer visitDivision(Division division, Void __) {
		Integer leftOp = division.leftOperand.accept(this);
		Integer rightOp = division.rightOperand.accept(this);

		if(rightOp == 0) {
			throw new ArithmeticException();
		}

		return Math.floorDiv(leftOp, rightOp);
	}
	@Override public Integer visitAddition(Addition addition, Void __) {
		return Math.addExact(addition.leftOperand.accept(this), addition.rightOperand.accept(this));
	}
	@Override public Integer visitSubtraction(Subtraction subtraction, Void __) {
		return Math.subtractExact(subtraction.leftOperand.accept(this), subtraction.rightOperand.accept(this));
	}

}
