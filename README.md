# MongoDB Related Projects

  - [Before running](#before-running)
  - [Project 1 - Java Quick Start](#project-1---java-quick-start)
    - [Youtube / Twitch Video](#youtube--twitch-video)
    - [CRUD operations](#crud-operations)
    - [Mapping POJOs](#mapping-pojos)
    - [Aggregation pipeline](#aggregation-pipeline)
  - [Project 2 - Hotel Application](#project-2---hotel-application)
    - [API calls](#api-calls)
      - [Get all hotels](#get-all-hotels)
      - [Find hotel](#find-hotel)
      - [Find hotels with price less than a specific amount](#find-hotels-with-price-less-than-a-specific-amount)
      - [Find all by city](#find-all-by-city)
      - [Find all by country](#find-all-by-country)
      - [Get recommended hotels](#get-recommended-hotels)
      - [Add new hotel](#add-new-hotel)
      - [Update hotel](#update-hotel)
      - [Delete hotel](#delete-hotel)

## Before running
`mongodb.uri` value in `application.properties` should be replaced with the value obtained from your cluster (_MongoDB Atlas > Cluster > Connect > Connect your application_).

## Project 1 - Java Quick Start
### Youtube / Twitch Video
Based on [Developing with MongoDB and Java](https://www.youtube.com/watch?v=bkhXHiracs8).

`quickstart.twitchclip.Application` class is based on the clip.

### CRUD operations
The associated blog post is [here](https://developer.mongodb.com/quickstart/java-setup-crud-operations).

`Create`, `Read`, `Update` and `Delete` classes can be independently run and they showcase different available operations. 

### Mapping POJOs
The associated blog post is [here](https://developer.mongodb.com/quickstart/java-mapping-pojos).

The `main` method is under `MappingPOJOs` with `Grade` and `Score` mapping the associated documents.

### Aggregation pipeline
The associated blog post is [here](https://developer.mongodb.com/quickstart/java-aggregation-pipeline).

`AgreggationPipelineForZips` retrieves the 3 most populated cities in Texas, USA.

`AggregationPipelineForPosts` retrieves the 3 most popular tags and their associated titles.

## Project 2 - Hotel Application
Based on a tutorial from [Romanian Coder](https://www.youtube.com/watch?v=Hu-cyytqfp8).

### API calls
#### Get all hotels
 * __URI:__ _hotels/all_
 * __Method:__ _GET_

 * __Params:__ <br/>
    * required: - <br/>
    * optional: - <br/>
        
 * __Success response:__
    * Code: 200 OK <br/>
    * Content:
    ```
    [
        {
            "id": "5f72204dfc71c03cb7a1ed5b",
            "name": "Hotel 1",
            "pricePerNight": 130,
            "address": {
                "city": "Paris",
                "country": "France"
            },
            "reviews": [
                {
                    "userName": "John",
                    "rating": 9,
                    "approved": true
                },
                {
                    "userName": "Mary",
                    "rating": 8,
                    "approved": false
                }
            ]
        },
        {
            "id": "5f72204dfc71c03cb7a1ed5c",
            "name": "Hotel 2",
            "pricePerNight": 90,
            "address": {
                "city": "London",
                "country": "UK"
            },
            "reviews": [
                {
                    "userName": "Alex",
                    "rating": 8,
                    "approved": false
                }
            ]
        },
        {
            "id": "5f72204dfc71c03cb7a1ed5d",
            "name": "Hotel 3",
            "pricePerNight": 200,
            "address": {
                "city": "Rome",
                "country": "Italy"
            },
            "reviews": null
        }
    ]
    ```

#### Find hotel
 * __URI:__ _hotels/:id_
 * __Method:__ _GET_

 * __URL params:__ <br/>
    * required: <br/>
        `id=[String]`
    * optional: - <br/>
        
 * __Success response:__
    * Code: 200 OK <br/>
    * Content:
    ```
    {
        "id": "5f722b898e0bbd2c863c71e7",
        "name": "Hotel 1",
        "pricePerNight": 130,
        "address": {
            "city": "Paris",
            "country": "France"
        },
        "reviews": [
            {
                "userName": "John",
                "rating": 9,
                "approved": true
            },
            {
                "userName": "Mary",
                "rating": 8,
                "approved": false
            }
        ]
    }
    ```

#### Find hotels with price less than a specific amount
 * __URI:__ _hotels/price/:maxPrice_
 * __Method:__ _GET_

 * __URL params:__ <br/>
    * required: <br/>
        `maxPrice=[Integer]`
    * optional: - <br/>
        
 * __Success response:__
    * Code: 200 OK <br/>
    * Content:
    ```
    [
        {
            "id": "5f722b898e0bbd2c863c71e7",
            "name": "Hotel 1",
            "pricePerNight": 130,
            "address": {
                "city": "Paris",
                "country": "France"
            },
            "reviews": [
                {
                    "userName": "John",
                    "rating": 9,
                    "approved": true
                },
                {
                    "userName": "Mary",
                    "rating": 8,
                    "approved": false
                }
            ]
        },
        {
            "id": "5f722b898e0bbd2c863c71e8",
            "name": "Hotel 2",
            "pricePerNight": 90,
            "address": {
                "city": "London",
                "country": "UK"
            },
            "reviews": [
                {
                    "userName": "Alex",
                    "rating": 8,
                    "approved": false
                }
            ]
        }
    ]
    ```

#### Find all by city
(Query implementation.)
 * __URI:__ _hotels/address/city/:city_
 * __Method:__ _GET_

 * __URL params:__ <br/>
    * required: <br/>
        `city=[String]`
    * optional: - <br/>
        
 * __Success response:__
    * Code: 200 OK <br/>
    * Content:
    ```
    [
        {
            "id": "5f722b898e0bbd2c863c71e8",
            "name": "Hotel 2",
            "pricePerNight": 90,
            "address": {
                "city": "London",
                "country": "UK"
            },
            "reviews": [
                {
                    "userName": "Alex",
                    "rating": 8,
                    "approved": false
                }
            ]
        }
    ]
    ```

#### Find all by country
(DSL implementation.)
 * __URI:__ _hotels/address/country/:country_
 * __Method:__ _GET_

 * __URL params:__ <br/>
    * required: <br/>
        `country=[String]`
    * optional: - <br/>
        
 * __Success response:__
    * Code: 200 OK <br/>
    * Content:
    ```
    [
        {
            "id": "5f722b898e0bbd2c863c71e8",
            "name": "Hotel 2",
            "pricePerNight": 90,
            "address": {
                "city": "London",
                "country": "UK"
            },
            "reviews": [
                {
                    "userName": "Alex",
                    "rating": 8,
                    "approved": false
                }
            ]
        }
    ]
    ```

#### Get recommended hotels
 * __URI:__ _hotels/recommendation_
 * __Method:__ _GET_

 * __Params:__ <br/>
    * required: - <br/>
    * optional: - <br/>
        
 * __Success response:__
    * Code: 200 OK <br/>
    * Content:
    ```
    [
        {
            "id": "5f722b898e0bbd2c863c71e8",
            "name": "Hotel 2",
            "pricePerNight": 90,
            "address": {
                "city": "London",
                "country": "UK"
            },
            "reviews": [
                {
                    "userName": "Alex",
                    "rating": 8,
                    "approved": false
                }
            ]
        }
    ]
    ```

#### Add new hotel
 * __URI:__ _hotels_
 * __Method:__ _POST_
 
 * __Data params:__ <br/>
    * required: <br/>
        hotel=[Hotel]
        ```
        {
            "name": "Hotel 4",
            "pricePerNight": 150,
            "address": {
                "city": "Stockholm",
                "country": "Sweden"
            },
            "reviews": [
                {
                    "userName": "Anna",
                    "rating": 7,
                    "approved": false
                }
            ]
        }
        ```
    * optional: - <br/>
        
 * __Success response:__
    * Code: 200 OK <br/>
    * Content:
    ```
    {
        "id": "5f722b9b8e0bbd2c863c71ea",
        "name": "Hotel 4",
        "pricePerNight": 150,
        "address": {
            "city": "Stockholm",
            "country": "Sweden"
        },
        "reviews": [
            {
                "userName": "Anna",
                "rating": 7,
                "approved": false
            }
        ]
    }
    ```

#### Update hotel
 * __URI:__ _hotels/:id_
 * __Method:__ _PUT_

 * __URL params:__ <br/>
    * required: <br/>
        `id=[String]`
    * optional: - <br/>
    
 * __Data params:__ <br/>
    * required: <br/>
       hotel=[Hotel]
       ```
       {
           "name": "Hotel 4",
           "pricePerNight": 170,
           "address": {
               "city": "Gothenburg",
               "country": "Sweden"
           },
           "reviews": [
               {
                   "userName": "Anna",
                   "rating": 7,
                   "approved": false
               },
               {
                   "userName": "Hanna",
                   "rating": 9,
                   "approved": true
               }
           ]
       }
       ```
    * optional: - <br/>
        
 * __Success response:__
    * Code: 200 OK <br/>
    * Content:
    ```
    {
        "id": "5f722b9b8e0bbd2c863c71ea",
        "name": "Hotel 4",
        "pricePerNight": 170,
        "address": {
            "city": "Gothenburg",
            "country": "Sweden"
        },
        "reviews": [
            {
                "userName": "Anna",
                "rating": 7,
                "approved": false
            },
            {
                "userName": "Hanna",
                "rating": 9,
                "approved": true
            }
        ]
    }
    ```

#### Delete hotel
 * __URI:__ _hotels/:id_
 * __Method:__ _DELETE_

 * __URL params:__ <br/>
    * required: <br/>
        `id=[String]`
    * optional: - <br/>
    
 * __Success response:__
    * Code: 204 NO CONTENT <br/>

