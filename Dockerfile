FROM ubuntu:24.04@sha256:c35e29c9450151419d9448b0fd75374fec4fff364a27f176fb458d472dfc9e54

ENV DEBIAN_FRONTEND=noninteractive

# Base tooling: python/pip, git, plus a JDK + Maven to build the bootable jar
# and postgresql to host the "skyrim" database.
RUN apt-get update && apt-get install -y --no-install-recommends \
        python3 \
        python3-pip \
        git \
        openjdk-17-jdk-headless \
        maven \
        postgresql \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /opt

RUN git clone https://github.com/njsoly/skyrim-alchemy.git
# To pin a specific commit instead of the default branch, use:
# RUN git clone https://github.com/njsoly/skyrim-alchemy.git && \
#     cd skyrim-alchemy && \
#     git checkout <commit-sha>

WORKDIR /opt/skyrim-alchemy

# Build the project into a bootable (fat) jar via the maven-shade-plugin.
RUN mvn -B -q clean package -DskipTests

# Create the "skyrim" Postgres database.
USER postgres
RUN service postgresql start \
    && psql --command "CREATE DATABASE skyrim;" \
    && service postgresql stop
USER root

CMD ["bash"]
