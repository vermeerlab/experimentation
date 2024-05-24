package ee.sample.apps.common.presentation.openapi;

import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import java.util.List;

/** ResponseFactory. */
public class ResponseFactory {

  /**
   * 成功.
   *
   * @return レスポンス（レスポンスボディなし）
   */
  public static Response success() {
    var entity = new ResponseBody<Void>(true, null);
    var responseBuilder = Response.ok(new GenericEntity<ResponseBody<Void>>(entity) {});
    return responseBuilder.build();
  }

  /**
   * 成功.
   *
   * @param <T> レスポンスボディの型
   * @param body レスポンスボティ
   * @return レスポンス
   */
  public static <T> Response success(T body) {
    var entity = new ResponseBody<T>(true, body);
    var responseBuilder = Response.ok(new GenericEntity<ResponseBody<T>>(entity) {});
    return responseBuilder.build();
  }

  /**
   * 成功.
   *
   * @param <T> レスポンスボディの型
   * @param body レスポンスボティ
   * @return レスポンス
   */
  public static <T> Response success(List<T> body) {
    var entity = new ResponseListBody<T>(true, body);
    var responseBuilder = Response.ok(new GenericEntity<ResponseListBody<T>>(entity) {});
    return responseBuilder.build();
  }

  /**
   * 成功 for POST.
   *
   * @return レスポンス（レスポンスボディなし）
   */
  public static Response created() {
    var entity = new ResponseBody<Void>(true, null);
    var responseBuilder = Response.ok(new GenericEntity<ResponseBody<Void>>(entity) {});
    return responseBuilder.status(Response.Status.CREATED).build();
  }

  /**
   * 成功 for POST.
   *
   * @param <T> レスポンスボディの型
   * @param body レスポンスボティ
   * @return レスポンス
   */
  public static <T> Response created(T body) {
    var entity = new ResponseBody<T>(true, body);
    var responseBuilder = Response.ok(new GenericEntity<ResponseBody<T>>(entity) {});
    return responseBuilder.status(Response.Status.CREATED).build();
  }

  /**
   * 成功 for PUT or DELETE.
   *
   * @return レスポンス（レスポンスボディなし）
   */
  public static Response noContent() {
    var entity = new ResponseBody<Void>(true, null);
    var responseBuilder = Response.ok(new GenericEntity<ResponseBody<Void>>(entity) {});
    return responseBuilder.status(Response.Status.NO_CONTENT).build();
  }

  /**
   * 成功 for GET Download.
   *
   * <p>デフォルトのMediaTypeは{@Code MediaType.APPLICATION_OCTET_STREAM}
   *
   * @param stream 出力ファイル
   * @param fileName 出力ファイル名
   * @return レスポンス
   */
  public static Response download(StreamingOutput stream, String fileName) {
    return ResponseFactory.download(stream, fileName, MediaType.APPLICATION_OCTET_STREAM);
  }

  /**
   * 成功 for GET Download.
   *
   * @param stream 出力ファイル
   * @param fileName 出力ファイル名
   * @param mediaType MediaType
   * @return レスポンス
   */
  public static Response download(StreamingOutput stream, String fileName, MediaType mediaType) {
    return ResponseFactory.download(stream, fileName, mediaType.toString());
  }

  /**
   * 成功 for GET Download.
   *
   * @param stream 出力ファイル
   * @param fileName 出力ファイル名
   * @param mediaType MediaType
   * @return レスポンス
   */
  public static Response download(StreamingOutput stream, String fileName, String mediaType) {
    return Response.ok(stream, mediaType)
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
        .build();
  }

  /**
   * 失敗.
   *
   * <p>デフォルトとして422
   *
   * @param <T> レスポンスボディの型
   * @param body レスポンスボティ
   * @return レスポンス
   */
  public static <T> Response fail(T body) {
    // UNPROCESSABLE_ENTITY:422
    return ResponseFactory.fail(body, 422);
  }

  /**
   * 失敗.
   *
   * @param <T> レスポンスボディの型
   * @param body レスポンスボティ
   * @param status HttpStatus
   * @return レスポンス
   */
  public static <T> Response fail(T body, int status) {
    var entity = new ResponseBody<T>(false, body);
    var responseBuilder = Response.ok(new GenericEntity<ResponseBody<T>>(entity) {});
    return responseBuilder.status(status).build();
  }

  /**
   * 失敗.
   *
   * <p>デフォルトとして422
   *
   * @param <T> レスポンスボディの型
   * @param body レスポンスボティ
   * @return レスポンス
   */
  public static <T> Response fail(List<T> body) {
    // UNPROCESSABLE_ENTITY:422
    return ResponseFactory.fail(body, 422);
  }

  /**
   * 失敗.
   *
   * @param <T> レスポンスボディの型
   * @param body レスポンスボティ
   * @param status HttpStatus
   * @return レスポンス
   */
  public static <T> Response fail(List<T> body, int status) {
    var entity = new ResponseListBody<T>(false, body);
    var responseBuilder = Response.ok(new GenericEntity<ResponseListBody<T>>(entity) {});
    return responseBuilder.status(status).build();
  }
}
