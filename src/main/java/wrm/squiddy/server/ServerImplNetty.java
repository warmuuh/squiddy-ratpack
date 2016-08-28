package wrm.squiddy.server;

import static org.pk11.rxnetty.router.Dispatch.using;
import static org.pk11.rxnetty.router.Dispatch.withParams;
import static rx.Observable.just;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.pk11.rxnetty.router.Router;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.reactivex.netty.channel.AllocatingTransformer;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerInterceptorChain;
import io.reactivex.netty.protocol.http.server.HttpServerInterceptorChain.TransformingInterceptor;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.util.HttpContentStringLineDecoder;
import lombok.Setter;
import lombok.SneakyThrows;
import rx.Observable;

@Singleton
public class ServerImplNetty {
	
	@Inject @Setter
	private TestResource testResource;
	

	@SneakyThrows
	@PostConstruct
	public void setup(){
		HttpServer<String, ByteBuf> server = HttpServer.newServer(8080)
				.<String, ByteBuf>addChannelHandlerLast("line-decoder", HttpContentStringLineDecoder::new)
				.start(getInteceptors()
						.end(using(getRoutes())));
		
		server.awaitShutdown();
	}

	
	private HttpServerInterceptorChain<String, ByteBuf, String, Object> getInteceptors(){
		return HttpServerInterceptorChain.<String, ByteBuf>start()
				.<String, Object>nextWithTransform(serializeJsonResponse());
	}


	private TransformingInterceptor<String, ByteBuf, String, Object> serializeJsonResponse() {
		return handler -> 
		(request, response) -> 
			handler.handle(request, response.transformContent(new AllocatingTransformer<Object, ByteBuf>() {
					@Override @SneakyThrows
					public List<ByteBuf> transform(Object value, ByteBufAllocator allocator) {
						ObjectMapper mapper = new ObjectMapper();
						byte[] content = mapper.writeValueAsBytes(value);
						return Collections.singletonList(allocator.buffer().writeBytes(content));
					}
			}));
	}
	
	private Router<String, Object> getRoutes() {
		return new Router<String, Object>()
		  .GET("/tests/:id", withParams((params, request, response)->{
			  response.setStatus(HttpResponseStatus.OK);
			  return response.write(testResource.getTest(params.get("id")).map(desc -> (Object)desc));
		  }))
		  .notFound(this::handle404);
	}
	 
	
	public Observable<Void> handle404(HttpServerRequest<String> req, HttpServerResponse<Object> res) {
		res.setStatus(HttpResponseStatus.NOT_FOUND);
		return res.writeString(just("Mapping not found"));
	}
	
	
}
