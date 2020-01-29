FROM oracle/graalvm-ce:1.0.0-rc15 as graalvm
COPY . /home/app/hello-world
WORKDIR /home/app/hello-world
RUN native-image --allow-incomplete-classpath  --no-server -cp build/libs/simple-http-*.jar lt.gzeskas.http.SimpleNettyHttpServerApp
RUN echo $(ls -la /home/app/hello-world)

FROM frolvlad/alpine-glibc
EXPOSE 8080
COPY --from=graalvm /home/app/hello-world .
ENTRYPOINT ["./lt.gzeskas.http.simplenettyhttpserverapp"]