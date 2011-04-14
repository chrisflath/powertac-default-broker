/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an
 * "AS IS" BASIS,  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.powertac.common

import org.joda.time.Instant
import org.powertac.common.transformer.BrokerConverter
import com.thoughtworks.xstream.annotations.*

/**
 * Represents interest charges and payments for brokers
 * @author John Collins
 */
@XStreamAlias("bank-tx")
class BankTransaction
{
  /** The broker for this transactions */
  @XStreamConverter(BrokerConverter)
  Broker broker
  
  /** The amount of this transaction */
  @XStreamAsAttribute
  BigDecimal amount = 0.0
  
  /** When this transaction occurred  */
  Instant postedTime
  
  static constraints = {
    postedTime(nullable: false)
    broker(nullable: false)
  }
}
