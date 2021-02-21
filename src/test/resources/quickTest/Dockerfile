FROM ubuntu:20.04
RUN apt-get update && apt-get install -y \
    gcc-arm-linux-gnueabi \
    qemu-user

WORKDIR /src
ENTRYPOINT "bash"