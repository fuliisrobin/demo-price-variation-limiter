<!-- TOC -->
* [Problem Definition](#problem-definition)
* [Examples](#examples)
  * [Tick size](#tick-size)
  * [Tick table](#tick-table)
* [Requirements](#requirements)
  * [Price Variation Limiter should apply to multiple types of equity products.  can set different limit for different type of products.](#price-variation-limiter-should-apply-to-multiple-types-of-equity-products-can-set-different-limit-for-different-type-of-products-)
  * [Price Variation Limiter should support multiple ways to calculate the price variations.](#price-variation-limiter-should-support-multiple-ways-to-calculate-the-price-variations-)
  * [Price Variation Limiter should support different validation scenarios](#price-variation-limiter-should-support-different-validation-scenarios)
  * [Price Variation Limiter should support different source of reference price.](#price-variation-limiter-should-support-different-source-of-reference-price-)
* [Acceptance Criteria](#acceptance-criteria)
* [Tests Data & Scenarios](#tests-data--scenarios-)
  * [Tick table of KS200400F5.KS](#tick-table-of-ks200400f5ks)
  * [Reference price](#reference-price)
  * [Orders and output](#orders-and-output)
    * [Option](#option)
    * [Stock](#stock)
<!-- TOC -->
# Problem Definition
Trading with a price far away from market can be very risky. This is called Price Variation Variation and can happened to due a variety of causes, such as mispricing or human error. In algo trading, price variation result in a huge finical loss.
To minimize these risks, you can configure to restrict the max and min price allowed to trade an instrument with. If trader attempts to trade the instrument with an order price beyond the configured limit, an alert will be triggered to traders.

# Examples
Let's say that you've configured a price variation limit of 20%. This means that if you try to send an order witch price away from a reference price by 20%, the price variation limit will be violated.

As a simple example

If a trader tries to submit an order with following details as 'Buy 100 lots of Apple Inc at 300 $, which reference price is 230 $'.  then price variation limit will be activated, an alert message will be sent to user.

## Tick size
Tick size is the minimum price change up or down of a trading instrument in a market.

## Tick table
Tick table for Apple Inc, following tick table means,
* if price is between 0 and 20, then tick size is 1 $.
* if price is between 20(include 20) and 100, then tick size is 5 $. 
* if price is above 100(include 100), then tick size is 10 $

| Min | Max | Tick Size |
|-----|-----|-----------|
| 0   | 20  | 1         |
| 20  | 100 | 5         |
| 100 |     | 10        |

# Requirements
Implement a price variation limiter with following features, the input is an Order command. the output is an indicator of whether price variation limiter is activated or not and why. 

## Price Variation Limiter should apply to multiple types of equity products.  can set different limits for different type of products. 
* Products type: Stock, Option, Future
## Price Variation Limiter should support multiple ways to calculate the price variations. 
* By percentage: In above example, the price variation by percentage is (300 - 230 ) / 230 = 30.43%
* By absolute value: In above example, the price variation by absolute  value is 300 - 230 = 70
* By Tick size: In above example, the price variation by tick size is (300 - 230) / 10 = 7 (T) 
## Price Variation Limiter should support different validation scenarios
* Only at advantage:  this means that Price Variation Limiter only can be activated when HSBC is at an advantage situation. For example, buy something at a price much lower than refrence price,  or sell something at a price much higher than refrence price. This can be a requirement from regulatory compliance
* Only at disadvantage: this means that Price Variation Limiter only can be activated when HSBC is at a disadvantage situation. For example, buy something at a price much higher than refrence price,  or sell something at a price much lower than refrence price. This mainly to protect HSBC from losing money.
* Both: sometimes Price Variation Limiter need to be activated in both advantage and disadvantage case.
## Price Variation Limiter should support different sources of reference price with the following fallback logic 
* last traded price should be firstly used if available
* close price should be fallen back to if above is not available
* theo price should be fallen back to if above is not available
* No trades should be allowed if there are not any reference prices.

# Acceptance Criteria
* TODO

# Tests Data & Scenarios 
## Tick table of KS200400F5.KS

| Min  | Max  | Tick Size |
|------|------|-----------|
| 0.0  | 10.0 | 0.01      |
| 10.0 |      | 0.05      |

## Reference price

| Instrument     | Product Type | Theo Price | Last Trade Price | Close Price |
|----------------|--------------|------------|------------------|-------------|
| HSIZ4          | Future       | 19000	     | 19010            | 19020       |
| KS200400F5.KS  | Option       | 8.91       | 8.88             | 8.84        |
| VOD.L          | Stock        | 240        | 245              | 231         |

## Orders and output
### Option

| No | Instrument    | Side | Price  | Alert | Variation                              | Description         |
|----|---------------|------|--------|-------|----------------------------------------|---------------------|
| 0  | KS200400F5.KS | Buy  | 8.81   | No    | (8.81-8.81)/0.01 = 0                   | 0 < 8, pass         |
| 1  | KS200400F5.KS | Buy  | 	8.72  | Yes   | (8.81-8.72)/0.01 = 9                   | 9 >= 8, block       |
| 2  | KS200400F5.KS | Buy  | 8.90   | No    | (8.81-8.90)/0.01 = -9                  | buy higher, pass    |
| 3  | KS200400F5.KS | Sell | 8.92   | No    | (8.91-8.92)/0.01 = -1                  | abs(-1) < 8, pass   |
| 4  | KS200400F5.KS | Sell | 8.82   | No    | (8.91-8.82)/0.01 = 9                   | sell lower, pass    |
| 5  | KS200400F5.KS | Sell | 9.00   | Yes   | (8.91-9.00)/0.01 = -9	                 | abs(-9) >= 8, block |
| 6  | KS200400F5.KS | Buy  | 9.94   | No    | (9.93-9.94)/0.01 = -1                  | abs(-1) < 8, pass   |
| 7  | KS200400F5.KS | Buy  | 9.84   | Yes   | (9.93-9.84)/0.01 = 9                   | 9 >= 8, block       |
| 8  | KS200400F5.KS | Buy  | 10.10	 | No    | (9.93-10)/0.01 + (10-10.10)/0.05 = -9	 | buy higher, pass    |
| 9  | KS200400F5.KS | Sell | 9.94   | No    | (9.95-9.94)/0.01 = 1                   | 1 < 8, pass         |
| 10 | KS200400F5.KS | Sell | 9.87	  | No    | 9.95-9.87)/0.01 = 8                    | sell lower, pass    |
| 11 | KS200400F5.KS | Sell | 10.20  | Yes   | (9.95-10)/0.01 + (10-10.20)/0.05 = -9  | abs(-9) >= 8, block |
| 12 | KS200400F5.KS | Buy  | 10.10  | No    | (10.15-10.10)/0.05 = 1                 | 1 < 8, pass         |
| 13 | KS200400F5.KS | Buy  | 9.94   | Yes   | 10.15-10)/0.05 + (10-9.94)/0.01 = 9    | 9 >= 8, block       |
| 14 | KS200400F5.KS | Buy  | 10.06	 | No    | (10.15-10.60)/0.05 = -9                | buy higher, pass    |
| 15 | KS200400F5.KS | Sell | 	10.30 | No    | (10.25-10.30)/0.05 = -1                | abs(-1) < 8, pass   |
| 16 | KS200400F5.KS | Sell | 9.96   | No    | (10.25-10)/0.05 + (10-9.96)/0.01 = 9   | sell lower, pass    |
| 17 | KS200400F5.KS | Sell | 10.70  | Yes   | (10.25-10.70)/0.05 = -9                | abs(-9) >= 8, block |
### Stock

| No | Instrument | Side  | Price | Alert | Variation        | Description      |
|----|------------|-------|-------|-------|------------------|------------------|
| 1	 | VOD.L	     | Buy   | 	245  | 	No	  | 245 - 245 = 0	   | 0 < 10, pass     |
| 2	 | VOD.L	     | Buy   | 	255  | 	Yes	 | 255 - 245 = 10	  | 10 >= 10, block  |
| 3	 | VOD.L	     | Buy   | 	265  | 	No	  | (265 - 245) = 20 | 	20 >= 10, block |
| 4	 | VOD.L	     | Sell  | 	245  | 	No	  | 245 - 245 = 0	   | 0 < 10, pass     |
| 5	 | VOD.L	     | Sell	 | 235   | 	Yes	 | 245 - 235 = 10	  | 10 >= 10, block  |
| 6	 | VOD.L	     | Sell	 | 225   | 	Yes	 | 245 - 225= 20	   | 20 >= 10, block  |