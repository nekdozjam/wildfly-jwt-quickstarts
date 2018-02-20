#!/bin/sh

echo "Generating private key..."
ssh-keygen -t rsa -b 2048 -f temp.pem

echo "Converting to PKCS8 format..."
openssl pkcs8 -topk8 -inform PEM -outform PEM -in temp.pem -out private_key.pem -nocrypt

echo "Generating public key..."
openssl rsa -in private_key.pem -pubout -outform PEM -out public_key.pem

rm temp.pem

echo "Done."