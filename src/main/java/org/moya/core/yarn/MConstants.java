/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.moya.core.yarn;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * Constants used in both Client and Application Master
 */
@InterfaceAudience.Public
@InterfaceStability.Unstable
public class MConstants {
	
	  /**
	   * List of ZooKeeper Hosts to use
	   */
	  public static final String ZOOKEEPERHOSTS = "ZOOKEEPERHOSTS";

  /**
   * Environment key name pointing to the the app master jar location
   */
  public static final String APPLICATIONMASTERJARLOCATION = "APPLICATIONMASTERJARLOCATION";

  /**
   * Environment key name denoting the file timestamp for the the app master jar. 
   * Used to validate the local resource. 
   */
  public static final String APPLICATIONMASTERJARTIMESTAMP = "APPLICATIONMASTERJARTIMESTAMP";

  /**
   * Environment key name denoting the file content length for the app master jar. 
   * Used to validate the local resource. 
   */
  public static final String APPLICATIONMASTERJARLEN = "APPLICATIONMASTERJARLEN";
  
  /**
   * Environment key name pointing to the runnable jar location
   */
  public static final String LIBSLOCATION = "LIBSLOCATION";

  /**
   * Environment key name denoting the file timestamp for the runnable jar. 
   * Used to validate the local resource. 
   */
  public static final String LIBSTIMESTAMP = "LIBSTIMESTAMP";

  /**
   * Environment key name denoting the file content length for the runnable jar 
   * Used to validate the local resource. 
   */
  public static final String LIBSLEN = "LIBSCRIPTLEN";
}