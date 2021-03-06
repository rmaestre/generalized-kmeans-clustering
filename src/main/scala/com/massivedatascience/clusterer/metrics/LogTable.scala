/*
 * Licensed to the Derrick R. Burns under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * Derrick R. Burns licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.massivedatascience.clusterer.metrics

import com.massivedatascience.clusterer.base.{Zero, One}

trait LogTable {
  private val logTable = new Array[Double](4096 * 1000)

  def fastLog(d: Double): Double = {
    if (d == Zero || d == One) {
      Zero
    } else {
      val x = d.toInt
      if (x < logTable.length && x.asInstanceOf[Double] == d) {
        if (logTable(x) == Zero) logTable(x) = Math.log(x)
        logTable(x)
      } else {
        Math.log(x)
      }
    }
  }
}
