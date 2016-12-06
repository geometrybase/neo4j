/*
 * Copyright (c) 2002-2016 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.cypher.internal.compiler.v3_2.phases

import org.neo4j.cypher.internal.compiler.v3_2.tracing.rewriters.RewriterCondition
import org.neo4j.cypher.internal.frontend.v3_2.ast.Statement
import org.neo4j.cypher.internal.frontend.v3_2.{InputPosition, SemanticState}

object CompilationState {

  /*
  The reason for all these traits is to make sure that for each stage, we add but do not loose information
   */
  sealed trait S1 {
    val queryText: String
    val startPosition: Option[InputPosition]
  }

  sealed trait S2 extends S1 {
    val statement: Statement
  }

  sealed trait S3 extends S2 {
    val semantics: SemanticState
  }

  sealed trait S4 extends S3 {
    val extractedParams: Map[String, Any]
    val postConditions: Set[RewriterCondition]
  }

  case class State1(queryText: String,
                    startPosition: Option[InputPosition]) extends S1 {
    def add(statement: Statement): State2 =
      State2(queryText, startPosition, statement)
  }

  case class State2(queryText: String,
                    startPosition: Option[InputPosition],
                    statement: Statement) extends S2 {
    def add(semantics: SemanticState): State3 =
      State3(queryText, startPosition, statement, semantics)
  }

  case class State3(queryText: String,
                    startPosition: Option[InputPosition],
                    statement: Statement,
                    semantics: SemanticState) extends S3 {
    def add(extractedParams: Map[String, Any], postConditions: Set[RewriterCondition]): State4 =
      State4(queryText, startPosition, statement, semantics, extractedParams, postConditions)
  }

  case class State4(queryText: String,
                    startPosition: Option[InputPosition],
                    statement: Statement,
                    semantics: SemanticState,
                    extractedParams: Map[String, Any], postConditions: Set[RewriterCondition]) extends S4

}
