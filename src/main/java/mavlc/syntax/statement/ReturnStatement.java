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
package mavlc.syntax.statement;

import mavlc.syntax.AstNodeVisitor;
import mavlc.syntax.SourceLocation;
import mavlc.syntax.expression.Expression;

import java.util.Objects;

/**
 * AST node representing a return statement.
 */
public class ReturnStatement extends Statement {
	
	public final Expression returnValue;
	
	/**
	 * @param sourceLocation Location of the node within the containing source file.
	 * @param returnValue Return value.
	 */
	public ReturnStatement(SourceLocation sourceLocation, Expression returnValue) {
		super(sourceLocation);
		this.returnValue = returnValue;
	}
	
	@Override
	public <RetTy, ArgTy> RetTy accept(AstNodeVisitor<? extends RetTy, ArgTy> visitor, ArgTy obj) {
		return visitor.visitReturnStatement(this, obj);
	}
	
	@Override public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		ReturnStatement statement = (ReturnStatement) o;
		return Objects.equals(returnValue, statement.returnValue);
	}
	
	@Override public int hashCode() {
		return Objects.hash(returnValue);
	}
}
