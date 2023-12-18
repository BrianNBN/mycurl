# Write Your Own curl

This challenge involves building your own version of curl, a command-line tool for transferring data with URLs. For the purpose of this challenge, the focus will be on using it as a tool for sending HTTP requests, commonly used to test or demonstrate RESTful APIs.
The Challenge - Building Your Own curl

In this challenge, you'll create a curl clone that focuses on making HTTP requests commonly used in RESTful APIs. The clone should be capable of connecting to a server and sending the HTTP methods: GET, DELETE, POST, and PUT.

As part of the challenge, you'll need to refer to the RFC that defines HTTP. The challenge will concentrate on HTTP 1.1, as defined in RFC9110.
## Step Zero

Setup your IDE/editor and choose a programming language suitable for writing network code.
## Step 1

Your goal in this step is to read the provided URL from the command line and print out the protocol text for a GET request. Implement code to parse the URL and extract essential information:

    The protocol (assuming HTTP for now).
    The host.
    The port (defaulting to 80 for HTTP if not provided).
    The path.

Example output:

`sh

% cccurl http://eu.httpbin.org/get
connecting to eu.httpbin.org
Sending request GET /get HTTP/1.1
Host: eu.httpbin.org
Accept: */*`

Step 2

Now, your aim is to send the GET request and display the response. Open a socket connection to the server, send the output from Step 1 to the server, read back the response, and print it out. Ensure the correct line termination after the headers.

Example:

`sh

% cccurl http://eu.httpbin.org/get
Sending request GET /get HTTP/1.1
Host: eu.httpbin.org
Accept: */*
Connection: close

HTTP/1.1 200 OK
Date: Fri, 15 Dec 2023 14:29:23 GMT
Content-Type: application/json
Content-Length: 227
Connection: close
Server: gunicorn/19.9.0
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true

{
"args": {},
"headers": {
"Accept": "*/*",
"Host": "eu.httpbin.org",
"X-Amzn-Trace-Id": "Root=1-657c62c3-26068fd12f977c810ce87090"
},
"url": "http://eu.httpbin.org/get"
}`

Step 3

Handle headers, printing them only if verbose mode is enabled. The output should look different based on verbosity.

Example without verbose mode:

sh

% cccurl http://eu.httpbin.org:80/get
{
"args": {},
"headers": {
"Accept": "*/*",
"Host": "eu.httpbin.org",
"X-Amzn-Trace-Id": "Root=1-657c6385-6cfbb92e76f346ed6f46b2b5"
},
"url": "http://eu.httpbin.org/get"
}

Example with verbose mode:

`sh

% cccurl -v http://eu.httpbin.org:80/get
> GET /get HTTP/1.1
> Host: eu.httpbin.org
> User-Agent: curl/8.1.2
> Accept: */*
>
< HTTP/1.1 200 OK
< Date: Fri, 15 Dec 2023 14:31:30 GMT
< Content-Type: application/json
< Content-Length: 260
< Connection: close
< Server: gunicorn/19.9.0
< Access-Control-Allow-Origin: *
< Access-Control-Allow-Credentials: true
<
{
  "args": {},
  "headers": {
    "Accept": "*/*",
    "Host": "eu.httpbin.org",
    "X-Amzn-Trace-Id": "Root=1-657c6342-627889715e4a2b61644a88fb"
  },
  "url": "http://eu.httpbin.org/get"
}`

## Step 4

Support POST by extending the command line to handle the POST method and send a JSON payload string to the server. Allow users to specify headers using the -H option and pass data to the server using the -d option.

Example:

```sh``

``% cccurl -X POST http://eu.httpbin.org/post \
-d '{"key": "value"}' \
-H "Content-Type: application/json"
{
"args": {},
"data": "{\"key\": \"value\"}",
"files": {},
"form": {},
"headers": {
"Accept": "*/*",
"Content-Length": "16",
"Content-Type": "application/json",
"Host": "eu.httpbin.org",
"X-Amzn-Trace-Id": "Root=1-657c69ae-6ea3b1ea7084a25843f4814c"
},
"json": {
"key": "value"
},
"url": "http://eu.httpbin.org/post"
}```

## Step 5

Support the PUT method, similar to POST.

Example:

`sh

% cccurl -X PUT http://eu.httpbin.org/put \
-d '{"key": "value2"}' \
-H "Content-Type: application/json"
{
"args": {},
"data": "{\"key\": \"value2\"}",
"files": {},
"form": {},
"headers": {
"Accept": "*/*",
"Content-Length": "17",
"Content-Type": "application/json",
"Host": "eu.httpbin.org",
"X-Amzn-Trace-Id": "Root=1-657c6c4a-46827c2d51082eef6e1ddc9a"
},
"json": {
"key": "value2"
},
"url": "http://eu.httpbin.org/put"
}`

Going Further

To extend this challenge:

    Add support for HEAD and PATCH.
    Handle keep-alive and use it to send multiple requests over the same TCP connection.
    Add support for SSL (and hence HTTPS).
