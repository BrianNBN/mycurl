Write Your Own curl
This challenge is to build your own version of curl.

So what is curl? It’s a command like tool for transferring data with URLs - for the sake of this challenge though we’re going to focus on it’s use as a tool for sending HTTP requests. As such it’s often used to test or demonstrate RESTful APIs. For example if you want to use the OpenAI API they offer examples using curl in their documentation.

The Challenge - Building Your Own curl
For this challenge we’re going to build a curl clone that is focused on making the HTTP requests we might use for a RESTful API. Our curl clone will be able to connect to a server and send the HTTP methods: GET, DELETE, POST and PUT.

As part of this challenge you will need to refer to the RFC that defines HTTP. To keep the challenge a reasonable size we’re going to focus on HTTP 1.1 as defined in RFC9110.

Step Zero
Like all the best programming languages we’re zero indexed! For this step, I’ll leave you to setup your IDE / editor and programming language of choice. Pick something you are happy writing network code in. Or one you want to learn how to write network code with.

Step 1
In this step your goal is to read the provided URL from the command line and print out the protocol text that would be sent for a GET request. A GET request is defined in Sections 9.3.1 of the RFC.

You’ll need to write the code to parse the URL and extract a few useful bits:

The protocol - though for the moment we’ll assume this is always going to be HTTP, but you should still check.
The host.
The port (we can default this to port 80 for HTTP if it is not provided).
The path.
When you run your solution you should get some output like this:

% cccurl http://eu.httpbin.org/get
connecting to eu.httpbin.org
Sending request GET /get HTTP/1.1
Host: eu.httpbin.org
Accept: */*
Or:

% cccurl http://eu.httpbin.org:80/get
connecting to eu.httpbin.org
Sending request GET /get HTTP/1.1
Host: eu.httpbin.org
Accept: */*
Here we’re going to use httpbin as a useful test site because it supports the methods we want to test.

Step 2
In this step your goal is to send the GET request and dump out the response. To send the request we’re going to need to open a socket connection to the server on the specified port (defaulting to 80 if it’s not specified - it would default to 443 for HTTPS if we supported that too).

Once you have opened a socket to the server you will need to send the output we printed in Step 1 to the server. Then read back the response and print it out. Make sure you send the correct line termination after the headers.

HTTP is a stateless protocol so there is no ‘connection’ to the server that stays open. However many servers support keeping a TCP connection open so you can make multiple requests without the overhead of re-creating a TCP connection for every request. For this challenge we’re going to tell the server to close the connection after every request. We can do that by sending the additional header: Connection: close

Once you’ve completed this step you should be able to send a request and get a response like this:

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
}
Here we’re dumping out the headers we sent and the full response we got back.

Step 3
In this step your goal is to handle the headers (don’t print them out if verbose is not enabled) and only show them if the verbose flag is enabled. In other words we’re going to tweak out code to look like this:

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
And with verbose mode (adding > and < to show which direction the message went):

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
}
Step 3
In this step your goal is to send a DELETE request to the server, this is a relatively simple change:

Accept the command line option -X <method> that allows us to specify the HTTP method.
Changing the method sent to DELETE. Once done it should look like this:
% cccurl -X DELETE http://eu.httpbin.org/delete
{
  "args": {},
  "data": "",
  "files": {},
  "form": {},
  "headers": {
    "Accept": "*/*",
    "Host": "eu.httpbin.org",
    "X-Amzn-Trace-Id": "Root=1-657c68d7-7b7a96900d27d3a952f99f65"
  },
  "json": null,
  "url": "http://eu.httpbin.org/delete"
}
Step 4
In this step your goal is to support POST in the way that it is used for most RESTful APIs. In essence that means we want to extend out command line to handle the POST method and to send a JSON payload string to the server. Something like this:

% cccurl -X POST http://eu.httpbin.org/post \
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
    "Host": "eu.httpbin.org
    "X-Amzn-Trace-Id": "Root=1-657c69ae-6ea3b1ea7084a25843f4814c"
  },
  "json": {
    "key": "value"
  },
  "url": "http://eu.httpbin.org/post"
}
Note that to do this we allowed the user to specify a new command line option -H and pass a string that we’d include in the headers. We also added support for the -d option which allows us to pass data to the server after the request. In this case the data is a string and the header we’ve passed tells the server that this string contains JSON.

Note that to send the JSON data to the server we now need to send the data after our headers and we’ll need to send a header that tells the server how long the data is. See Section 8.6 Content-Length in the RFC for details.

Step 5
In this step your goal is to support the PUT method. Again PUT is relatively similar to POST, once done it will look something like this:

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
}
Going Further
You can take this further by:

Adding support for HEAD and PATCH.
Handling keep alive and using it to send multiple requests over the same TCP connection.
Adding support for SSL (and hence HTTPS).
