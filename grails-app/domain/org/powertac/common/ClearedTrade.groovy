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

import org.powertac.common.transformer.ProductConverter
import com.thoughtworks.xstream.annotations.*

/**
 * A clearedTrade instance reports public trade information, i.e. the execution price and quantity.
 *
 * It relates to a single transaction, specifying the timeslot and product. The single clearedTrade
 * instances are aggregated in a collection by the auctioneer before they are sent to other entities/brokers.
 * In the periodic clearing this collection is collection is built after each clearing and includes
 * a clearedTrade instance per tradeable timslot and product.
 *
 * @author Daniel Schnurr
 */
@XStreamAlias("trade")
class ClearedTrade //implements Serializable 
{
  @XStreamAsAttribute
  String id = IdGenerator.createId()

  /** underlying timeslot for the trade (e.g. for a future the timeslot when real-world exchanges happen)*/
  Timeslot timeslot

  /** related product that was traded*/
  @XStreamConverter(ProductConverter)
  Product product

  /** clearing price of the trade */
  @XStreamAsAttribute
  BigDecimal executionPrice

  /** traded quantity of the specified product */
  @XStreamAsAttribute
  BigDecimal executionQuantity

  static constraints = {
    timeslot(nullable: false, blank: false)
    product(nullable: false)

    /** price and quantity have to be both not null (regular case) or both null (no execution) */
    executionPrice(nullable: true, validator: { price, ct ->
      (ct.executionQuantity && price) || (!ct.executionQuantity && !price)
    })
    executionQuantity(nullable: true, validator: { quantity, ct ->
      (ct.executionPrice && quantity) || (!ct.executionPrice && !quantity)
    })
  }
}
