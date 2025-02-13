# Demo Trading System

This project is a demo trading system that simulates trading operations.

## Prerequisites

Before you begin, ensure you have met the following requirements:
- Java Development Kit (JDK) installed (version 21 or higher)
- Apache Maven installed
- Eclipse IDE (or any other preferred IDE)

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/demo-trading-system.git
    ```
2. Navigate to the project directory:
    ```sh
    cd demo-trading-system
    ```

## Running the Project

1. Open the project in Eclipse:
    - Go to `File` > `Import...`
    - Select `Existing Maven Projects`
    - Browse to the project directory and click `Finish`

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

3. Run the application:
    - Right-click on the project in Eclipse
    - Select `Run As` > `Java Application`


## Quick test

1. Integration test with the webservice
- Test buy high (Pass)
    ```sh
     curl --data '{"price": "9.89", "symbol":"KS200400F5.KS", "quantity": "1000", "side": "Buy"}' -X POST -H 'content-type:application/json' http://10.1.50.43:8080/api/stock/order/place -vv
    ```
- Test buy low (Block)
    ```sh
     curl --data '{"price": "7.89", "symbol":"KS200400F5.KS", "quantity": "1000", "side": "Buy"}' -X POST -H 'content-type:application/json' http://10.1.50.43:8080/api/stock/order/place -vv
    ```
2. Test with unit test
- Edit and run core test cases at src/test/java/com/fuli/tradingsystem/order/validate/validators/PriceVariationLimiterOrderValidatorTest.java
## Usage

Once the application is running, you can interact with the trading system through the provided user interface or API endpoints.

## Contributing

To contribute to this project, please fork the repository and create a pull request. For major changes, please open an issue first to discuss what you would like to change.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.

## Contact

If you have any questions or feedback, please contact [44071928@qq.com](mailto:44071928@qq.com).
