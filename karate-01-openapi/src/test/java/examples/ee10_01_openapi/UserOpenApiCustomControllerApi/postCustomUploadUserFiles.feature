@openapi-file=src/test/java/examples/ee10_01_openapi/openapi.yml
Feature: ユーザーに関連するファイルを複数アップロードします


  Background:
    * url baseUrl

  @operationId=postCustomUploadUserFiles
  Scenario Outline: Test postCustomUploadUserFiles for <status> status code

    * def args = read(<testDataFile>)
    * def result = call read('postCustomUploadUserFiles.feature@operation') args
    * match result.responseStatus == <status>
    Examples:
      | status | testDataFile                                  |
      | 200    | 'test-data/postCustomUploadUserFiles_200.yml' |


  @operationId=postCustomUploadUserFiles
  Scenario: explore postCustomUploadUserFiles inline
    You can use this test to quickly explore your API.
    You can then copy this payload and paste it with Ctrl+Shift+V as an Example row above.
    * def payload =
      """
      {
        "statusCode": 200,
        "headers": {},
        "params": {
          "id": 0
        },
        "matchResponse": true
      }
      """
    * call read('postCustomUploadUserFiles.feature@operation') payload


  @ignore
  @operation @operationId=postCustomUploadUserFiles @openapi-file=src/test/java/examples/ee10_01_openapi/openapi.yml
  Scenario: operation UserOpenApiCustomControllerApi/postCustomUploadUserFiles
    * def args =
      """
      {
      auth: #(karate.get('auth')),
      headers: #(karate.get('headers')),
      params: #(karate.get('params')),
      statusCode: #(karate.get('statusCode')),
      matchResponse: #(karate.get('matchResponse'))
      }
      """
    * def authHeader = call read('classpath:karate-auth.js') args.auth
    * def headers = karate.merge(args.headers || {}, authHeader || {})
    Given path '/api/open-api-custom/', args.params.id, '/files'
    And headers headers
    And multipart file file = {read:'test-data/FileToUpload1.txt', filename:'FileToUpload1.txt',Content-type:'mulitpart/form-data'}
    And multipart file file = {read:'test-data/FileToUpload2.txt', filename:'FileToUpload2.txt',Content-type:'mulitpart/form-data'}
    When method POST
    # validate status code
    * if (args.statusCode && responseStatus != args.statusCode) karate.fail(`status code was: ${responseStatus}, expected: ${args.statusCode}`)
    # validate response body
    * if (args.matchResponse === true) karate.call('postCustomUploadUserFiles.feature@validate')

  @ignore @validate
  Scenario: validates postCustomUploadUserFiles response

    * def responseMatch =
      """
      {
        "ok": "##boolean"
      }
      """
    * match  response contains responseMatch

