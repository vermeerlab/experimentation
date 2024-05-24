@openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
Feature: HttpのNGステータスのレスポンス仕様
	多くのケースでは2xx以外のステータスはExceptionHandlerなどで一括して操作を行います.&lt;br&gt;
	各API毎に4xxのステータス仕様を記載したり、共通して出力するような実装を追加することもできますが&lt;br&gt;
	冗長な記載を減らすため本URLに4xxおよび5xxのAPI仕様は本URLへ集約します.
	クライアントにて4xx,5xxのHttpステータス毎の制御確認での利用も想定しています.

	Background:
		* url baseUrl

	@operationId=getHttpNgStatus
	Scenario Outline: Test getHttpNgStatus for <status> status code

		* def params = __row
		* def result = call read('getHttpNgStatus.feature@operation') { statusCode: #(+params.status), params: #(params), matchResponse: #(+params.matchResponse) }
		* match result.responseStatus == <status>

		Examples:
			| status | httpStatus | matchResponse |
			| 400    | 400        | true         |
			| 401    | 401        | false         |
			| 403    | 403        | false         |
			| 404    | 404        | false         |
			| 405    | 405        | false         |
			| 406    | 406        | false         |
			| 415    | 415        | false         |
			| 503    | 503        | false         |


	@operationId=getHttpNgStatus
	Scenario: explore getHttpNgStatus inline
		You can use this test to quickly explore your API.
		You can then copy this payload and paste it with Ctrl+Shift+V as an Example row above.
		* def statusCode = 400
		* def params = {"httpStatus":400}
		* def matchResponse = true
		* call read('getHttpNgStatus.feature@operation')


	@ignore
	@operation @operationId=getHttpNgStatus @openapi-file=karate-01-openapi/src/test/java/examples/ee10_01_openapi/openapi.yml
	Scenario: operation ExceptionControllerApi/getHttpNgStatus
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
		Given path '/api/openapi-http-ng-status/', args.params.httpStatus
		And headers headers
		When method GET
		# validate status code
		* if (args.statusCode && responseStatus != args.statusCode) karate.fail(`status code was: ${responseStatus}, expected: ${args.statusCode}`)
		# validate response body
		* if (args.matchResponse === true) karate.call('getHttpNgStatus.feature@validate')


	@ignore
	@validate
	Scenario: validates getHttpNgStatus response

		* def responseMatch =
			"""
			{
				"ok": false,
				"body": {
					"errors": [
						{
							"message": "構文が無効です",
							"messageCode": "HTTP_STATUS_BAD_REQUEST"
						}
					]
				}
			}
			"""
		* match  response contains responseMatch

