@openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Feature: ユーザー情報を検索します


  Background:
    * url baseUrl

  @operationId=getUserById
  Scenario Outline: Test getUserById for <status> status code

    * def params = __row
    * def result = call read('getUserById.feature@operation') { statusCode: #(+params.status), params: #(params), matchResponse: #(params.matchResponse) }
    * match result.responseStatus == <status>
    Examples:
      | status | id | matchResponse |
      | 200    | 1  | true          |


  @operationId=getUserById
  Scenario: explore getUserById inline
    You can use this test to quickly explore your API.
    You can then copy this payload and paste it with Ctrl+Shift+V as an Example row above.
    * def statusCode = 200
    * def params = {"id":1}
    * def matchResponse = true
    * call read('getUserById.feature@operation')


  @ignore
  @operation @operationId=getUserById @openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
  Scenario: operation UserControllerApi/getUserById
    * def args =
      """
      {
      auth: #(karate.get('auth')),
      headers: #(karate.get('headers')),
      params: #(karate.get('params')),
      body: #(karate.get('body')),
      statusCode: #(karate.get('statusCode')),
      matchResponse: #(karate.get('matchResponse'))
      }
      """
    * def authHeader = call read('classpath:karate-auth.js') args.auth
    * def headers = karate.merge(args.headers || {}, authHeader || {})
    Given path '/api/users/', args.params.id
    And headers headers
    When method GET
    # validate status code
    * if (args.statusCode && responseStatus != args.statusCode) karate.fail(`status code was: ${responseStatus}, expected: ${args.statusCode}`)
    # validate response body
    * if (args.matchResponse === true) karate.call('getUserById.feature@validate')

  @ignore @validate
  Scenario: validates getUserById response

    * def responseMatch =
      """
      {
        "body": {
          "gender": "MALE",
          "name": "##string",
          "id": "1"
        },
        "ok": "##boolean"
      }
      """
    * match  response contains responseMatch

