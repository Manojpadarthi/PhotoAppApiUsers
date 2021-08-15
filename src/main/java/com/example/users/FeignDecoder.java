/*
 * package com.example.users;
 * 
 * import java.io.IOException; import java.lang.reflect.Type;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.core.env.Environment; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.stereotype.Component; import
 * org.springframework.web.server.ResponseStatusException;
 * 
 * import feign.FeignException; import feign.Response; import
 * feign.codec.DecodeException; import feign.codec.ErrorDecoder;
 * 
 * //@Component public class FeignDecoder implements ErrorDecoder {
 * 
 * @Autowired Environment env;
 * 
 * 
 * 
 * @Override public Exception decode(String methodKey, Response response) {
 * 
 * 
 * 
 * switch(response.status()) {
 * 
 * case 400: break;
 * 
 * case 404: if(methodKey.contains("getAlbums")) { return new
 * ResponseStatusException(HttpStatus.valueOf(response.status())
 * ,env.getProperty("error.message")); }
 * 
 * break;
 * 
 * default: return new Exception(response.reason()); }
 * 
 * return null;
 * 
 * 
 * }
 * 
 * 
 * }
 */