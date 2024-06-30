package ee.sample.apps.common.presentation.openapi;

import ee.sample.plugin.openapi.OpenApiExampleObjectUtil;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.media.Schema;
import org.eclipse.microprofile.openapi.models.parameters.RequestBody;
import org.eclipse.microprofile.openapi.models.responses.APIResponse;

/** OpenAPIのOASFilterの実装. */
public class OpenApiFilter implements OASFilter {

  @Override
  public Operation filterOperation(Operation operation) {
    return OASFilter.super.filterOperation(operation);
  }

  @Override
  public RequestBody filterRequestBody(RequestBody requestBody) {
    OpenApiExampleObjectUtil.convertExternalValueToValue(requestBody.getContent());
    return OASFilter.super.filterRequestBody(requestBody);
  }

  @Override
  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  public APIResponse filterAPIResponse(APIResponse apiResponse) {
    OpenApiExampleObjectUtil.convertExternalValueToValue(apiResponse.getContent());
    return OASFilter.super.filterAPIResponse(apiResponse);
  }

  @Override
  public Schema filterSchema(Schema schema) {
    return OASFilter.super.filterSchema(schema);
  }

  @Override
  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  public void filterOpenAPI(OpenAPI openAPI) {
    OASFilter.super.filterOpenAPI(openAPI);
  }
}
