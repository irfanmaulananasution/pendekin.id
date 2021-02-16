# PENDEKIN.id

### Description / Overview
a simple and in-memory URL shortener service, as a CLI (Command-Line interface) app [an app runnable from terminal] that accepts input from the standard output.

### Technologies/Tools
- Java

### Installation
1. Simply clone this repository
2. access the code from local repository
3. compile the program
  ```
  javac LinkShortener.java
  ```
4. run the program
  ```
  java LinkShortener
  ```
  
### Using the program
there is 4 command you can use for this program
1. /shorten
This operation accepts a full URL (url) and desired shorten path (desired) as an input and
should be able to redirect request from pendekin.id/<short_path> to the full_url later
on.
If the desired is null, the service should automatically generate a random one.
  ```
  >>> /shorten?url=https://exampletest.com&short_path=example
  *the output will be as seen as below
  >>> https://pendekin.id/example
  
  *if there is no short_path parameter. the program will randomize it
  >>> /shorten?url=https://exampletest.com
  >>> https://pendekin.id/abcde
  ```
2. /redirect
This operation accepts a shortened URL and we should redirect the short URL to the full
URL by outputing the full URL. If the shortened URL does not exist or no longer exists in the
system, the system should be able to handle this gracefully and notify the user as well.

  ```
  >>> /redirect?url=https://pendekin.id/example
  >>> https://pendekin.id/exampletest.com
  
  *if there is no such short_path. the program give an error
  >>> /redirect?url=https://pendekin.id/ngawur
  >>> Error: ngawur not found in the service
  ```
  
3. /delete
This operation accepts a short link as an input and should delete/invalidate the short link in
the system so that subsequent /redirect request/operation will fail.
This operation accepts a shortened URL and we should redirect the short URL to the full
URL by outputing the full URL. If the shortened URL does not exist or no longer exists in the
system, the system should be able to handle this gracefully and notify the user as well.

  ```
  >>> /delete?url=https://pendekin.id/example
  >>> OK.
  
  *if there is no such short_link. the program give an error
  >>> /delete?url=https://pendekin.id/ngawur
  >>> Error: ngawur not found in the service
  ```
  
4. /count
This operation accepts a short link as an input via the url query parameters and should
output the number of views of the short link (the total number of redirect operation to that
short link).
If the given URL is not a valid short link in the service (have not been created in the system
or already deleted), the service should also be able to handle the case gracefully and
output:
"Error: <url> not found in the service".

  ```
  >>> /shorten?url=https://metricsui.com&short_path=metrics
  >>> https://pendekin.id/metrics
  
  >>> /count?url=https://pendekin.id/metrics
  >>> 0
  
  >>> /redirect?url=https://pendekin.id/metrics
  >>> https://metricsui.com
  
  >>> /count?url=https://pendekin.id/metrics
  >>> 1
  
  >>> /redirect?url=https://pendekin.id/metrics
  >>> https://metricsui.com
  
  >>> /count?url=https://pendekin.id/metrics
  >>> 2
  
  *if there is no such short_link. the program give an error
  >>> /count?url=https://pendekin.id/ngawur
  >>> Error: ngawur not found in the service.
  ```
 
### Author
- Irfan Maulana Nasution

### Changelog
This is the first version

### License
[MIT](./LICENSE.txt)