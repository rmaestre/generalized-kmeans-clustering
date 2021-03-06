/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
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
 *
 * This code is a modified version of the original Spark 1.0.2 K-Means implementation.
 */

package com.massivedatascience.clusterer.base

import com.massivedatascience.clusterer.util.XORShiftRandom
import org.apache.spark.rdd.RDD

import scala.reflect.ClassTag

class KMeansRandom[P <: FP : ClassTag, C <: FP : ClassTag](
                                                            pointOps: PointOps[P, C],
                                                            k: Int,
                                                            runs: Int)
  extends KMeansInitializer[P, C] {

  def init(data: RDD[P], seed: Int): Array[Array[C]] = {

    val filtered = data.filter(_.weight > 0)
    val count = filtered.count()
    if (runs * k <= count) {
      val x = filtered.takeSample(withReplacement = false, runs * k, new XORShiftRandom().nextInt())
      val centers = x.map(pointOps.pointToCenter).toSeq
      Array.tabulate(runs)(r => centers.slice(r * k, (r + 1) * k).toArray)
    } else if (k < count) {
      (0 to runs).toArray.map { _ => {
        filtered.takeSample(withReplacement = false, k,
          new XORShiftRandom().nextInt()).map(pointOps.pointToCenter)
      }
      }
    } else {
      (0 to runs).toArray.map { _ => filtered.collect().map(pointOps.pointToCenter)}
    }
  }


}


