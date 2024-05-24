Feature: OpenAPI定義の確認

  Background:
    * url 'http://localhost:8081'

  @ignore
  Scenario: compare open-api yaml
    Given path 'openapi'
    When method get
    Then status 200
    And match response == read('./openapi.txt')
