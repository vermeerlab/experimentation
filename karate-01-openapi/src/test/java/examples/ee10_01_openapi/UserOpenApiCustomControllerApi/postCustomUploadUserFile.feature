@openapi-file=src/test/java/examples/ee10_01_openapi/openapi.yml
Feature: ユーザーに関連するファイルをアップロードします


  Background:
    * url baseUrl

  @operationId=postCustomUploadUserFile
  Scenario Outline: Test postCustomUploadUserFile for <status> status code

    * def args = read(<testDataFile>)
    * def result = call read('postCustomUploadUserFile.feature@operation') args
    * match result.responseStatus == <status>
    Examples:
      | status | testDataFile                                 |
      | 200    | 'test-data/postCustomUploadUserFile_200.yml' |


  @operationId=postCustomUploadUserFile
  Scenario: explore postCustomUploadUserFile inline
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
    * call read('postCustomUploadUserFile.feature@operation') payload


  @ignore
  @operation @operationId=postCustomUploadUserFile @openapi-file=src/test/java/examples/ee10_01_openapi/openapi.yml
  Scenario: operation UserOpenApiCustomControllerApi/postCustomUploadUserFile
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
    Given path '/api/open-api-custom/', args.params.id, '/file'
    And headers headers
    And multipart file file = {read:'test-data/FileToUpload1.txt', filename:'FileToUpload1.txt',Content-type:'mulitpart/form-data'}
    When method POST
    # validate status code
    * if (args.statusCode && responseStatus != args.statusCode) karate.fail(`status code was: ${responseStatus}, expected: ${args.statusCode}`)
    # validate response body
    * if (args.matchResponse === true) karate.call('postCustomUploadUserFile.feature@validate')

  @ignore @validate
  Scenario: validates postCustomUploadUserFile response

    * def responseMatch =
      """
      {
        "ok": "##boolean"
      }
      """
    * match  response contains responseMatch

