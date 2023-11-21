# Create selfsigned certificate

## non-interactive and 10 years expiration

openssl req -x509 -newkey rsa:4096 -keyout private.key -out public.crt -sha256 -days 3650 -nodes -subj "/C=XX/ST=StateName/L=CityName/O=CompanyName/OU=CompanySectionName/CN=fake-ssl"

## Alterrnative wildcards modern openssl 
openssl req -x509 -newkey rsa:4096 -sha256 -days 3650 \
  -nodes -keyout private.key -out public.crt -subj "/CN=fake-ssl" \
  -addext "subjectAltName=DNS:fake-ssl,DNS:*.fake-ssl,IP:10.0.0.1"

## Alterrnative wildcards old openssl
openssl req -x509 -newkey rsa:4096 -sha256 -days 3650 \
  -nodes -keyout private.key -out public.crt -extensions san -config \
  <(echo "[req]"; 
    echo distinguished_name=req; 
    echo "[san]"; 
    echo subjectAltName=DNS:fake-ssl,DNS:*.fake-ssl,IP:10.0.0.1
    ) \
  -subj "/CN=fake-ssl"