package com.example;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.channel.socket.SocketChannel;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class WeatherServer {

    public static void main(String[] args) throws InterruptedException {
        //Netty管理事件循环的线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);        //接受客户端连接  
        EventLoopGroup workerGroup = new NioEventLoopGroup();       //处理客户端天气请求

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {     //初始化管道来，编/解码自定义数据操作
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new StringDecoder(), new StringEncoder(), new WeatherServerHandler());
                        }
                    });

            ChannelFuture future = serverBootstrap.bind(5001).sync();
            future.channel().closeFuture().sync();      //sync函数用于同步等待阻塞当前线程直到channel关闭
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static class WeatherServerHandler extends ChannelInboundHandlerAdapter {
        //有数据时调用

        private static final String apiKey = "8886b1e0be2f124ebaf72d6757865f0a"; 
        
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String city = (String) msg;
            System.out.println("客户端请求天气数据：" + city);
            String cityCode = getCityCode(city);

            if (cityCode == null) {
                ctx.writeAndFlush("没找到城市\r\n");
                return;
            }
            String weatherData = getWeatherData(cityCode);
            System.out.println("cityCode:" + cityCode);
            System.out.println("天气数据：" + weatherData);
            ctx.writeAndFlush(weatherData + "\r\n");         //将天气数据写回客户端
        }

        private String getWeatherData(String city) {
            try {
                // 使用高德API获取天气数据
                
                String url = String.format("https://restapi.amap.com/v3/weather/weatherInfo?city=%s&key=%s", city, apiKey);
                HttpClient client = HttpClients.createDefault();
                HttpGet request = new HttpGet(url);
                HttpResponse response = client.execute(request);

                String responseBody = EntityUtils.toString(response.getEntity());//获取HttpEntity类型数据，并转换为字符串
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(responseBody);
                JsonNode liveData = rootNode.path("lives").get(0);

                if (liveData.isObject()) {
                    String weather = liveData.path("weather").asText();
                    String temperature = liveData.path("temperature").asText();
                    return String.format("Weather: %s, Temperature: %s°C", weather, temperature);
                } else {
                    return "Weather data not found.";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Failed to get weather data.";
            }
        }

        public static String getCityCode(String cityName) {
            try {
                String url = String.format("https://restapi.amap.com/v3/config/district?keywords=%s&key=%s&subdistrict=1&extensions=all", 
                                           cityName, apiKey);
                HttpClient client = HttpClients.createDefault();
                HttpGet request = new HttpGet(url);
                HttpResponse response = client.execute(request);
                String responseBody = EntityUtils.toString(response.getEntity());
    
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(responseBody);
                JsonNode districtData = rootNode.path("districts");  // 获取第一个城市的返回数据
                for(JsonNode item : districtData){
                    JsonNode cityInfo = item.path("adcode");  // 取得城市编码'
                    if (!cityInfo.isMissingNode()) {
                        return cityInfo.asText();  // 返回城市编码
                    }
                }
                return null;  
            }    
            catch (Exception e) {
                e.printStackTrace();
                return null;  // 发生异常时返回null
            }
        }
    }
}
