#!/bin/bash

echo "##############################################################################"
echo "#                                   DOCKER                                   #"
echo "##############################################################################"

echo "Updating the apt package index"
apt-get update
echo ""

echo "Removing old versions of docker if exists"
apt-get remove docker docker-engine docker.io
echo ""

echo "Removing current version of docker if exists"
apt-get --assume-yes purge docker-ce
echo ""

echo "Remove old containers, images and networks if exists"
rm -Rf /var/lib/docker
echo ""

echo "Updating the apt package index"
apt-get update
echo ""

echo "Installing some packages for Docker"
apt-get --assume-yes install \
    apt-transport-https \
    ca-certificates \
    curl \
    software-properties-common
echo ""

echo "Adding Docker’s official GPG key"
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
echo ""

echo "Checking fingerprint"
echo "YOU SHOULD HAVE 9DC8 5822 9FC7 DD38 854A E2D8 8D81 803C 0EBF CD88 fingerprint"
apt-key fingerprint 0EBFCD88
echo ""

echo "Adding stable repository"
add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
echo ""

echo "Updating the apt package index"
apt-get update
echo ""

echo "Installing Docker"
apt-get --assume-yes install docker-ce
echo ""

echo "Listing all installed versions of Docker"
apt-cache madison docker-ce
echo ""

echo "##############################################################################"
echo "#                               DOCKER COMPOSE                               #"
echo "##############################################################################"

echo "Removing old versions of docker-compose if exists"
rm -f /usr/local/bin/docker-compose
echo ""

echo "Downloading last version of docker-compose"
curl -L https://github.com/docker/compose/releases/download/1.18.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
echo ""

echo "Making docker-compose script executable"
chmod +x /usr/local/bin/docker-compose
echo ""

echo "Checking docker-compose version"
docker-compose --version
echo ""

echo "##############################################################################"
echo "#                             SCRIPTS DEPLOYMENT                             #"
echo "##############################################################################"

echo "Creating folders"
mkdir -p ./config/mysql/docker-entrypoint-initdb.d
mkdir -p ./data/mysql
echo ""

echo "Downloading CREATE_ALL.sql"
curl -L https://raw.githubusercontent.com/LoicDelorme/Polytech-CryptoWallet-Backend/master/config/mysql/docker-entrypoint-initdb.d/CREATE_ALL.sql?token=AMTU8StN5WZfte3tZsc4sLobvavR7S_Yks5abbHawA%3D%3D -o ./config/mysql/docker-entrypoint-initdb.d/CREATE_ALL.sql
echo ""

echo "Downloading docker-compose.yml"
curl -L https://raw.githubusercontent.com/LoicDelorme/Polytech-CryptoWallet-Backend/master/docker-compose.yml?token=AMTU8VW0QnbLVbT0g4II37NncOAH_yXHks5aeHvCwA%3D%3D -o docker-compose.yml
echo ""

echo "Deploying all containers"
docker-compose up -d mysql-db-container
sleep 10
docker-compose up -d cryptowallet-backend-container
sleep 10
docker-compose up -d nginx-proxy-container
sleep 10
docker-compose up -d nginx-proxy-companion-container
echo ""

echo "##############################################################################"
echo "#                                 RUNNING?                                   #"
echo "##############################################################################"

echo "Listing all downloaded images"
docker images
echo ""

echo "Listing all running containers"
docker ps -a
echo ""

echo "##############################################################################"
echo "#                                  NETWORK                                   #"
echo "##############################################################################"

echo "Listing all network interfaces"
netstat -ntlp | grep LISTEN
echo ""