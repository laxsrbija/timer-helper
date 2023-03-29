FROM ghcr.io/graalvm/graalvm-ce:22 as build

WORKDIR /tmp
COPY . .

RUN ./mvnw -B -Pnative package native:compile-no-fork

FROM oraclelinux:9-slim

WORKDIR /opt/timer-helper
COPY --from=build /tmp/target/timer-helper .

EXPOSE 8080
CMD ["./timer-helper"]