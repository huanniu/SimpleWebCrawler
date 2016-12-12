Simple Web Crawler


The project is to parse a website by a given domain url. It reads through all its internal pages, and lists all the external links and images.

To run the project:

1. Download the project locally
2. Import it as a maven project
3. Run test SitemapCreatorTest
4. Check the output in file: sitemap


Given the time constraint, below are considerations of how the program is implemented.

1. A domain model is required for a web page, similar to a tree structure
2. Not all the methods are tested, but the main logic is covered
3. For loose coupling / testing purpose, I divided the functionality into three major components:

    a. PageParser: parse the document and create a web page object
    b. DocumentFetcher: connect to a url, fetch its content, and return the document object, simply using Jsoup api
    c. WebCrawler: the assembling point to iteratively build the web page object
4. The program hasn't tested against a real website, and just uses well formed urls for the two reasons:

    a. Time constraint
    b. The tests represent an abstract problem model. If the program works with the tests, it should work with the real problem but adjustments are required
5. Sitemap could be in different format. To keep it simple, I just output the result into a file


Further extensions and improvements:

1. Running the program against a real website. In this case, the program has to be adjusted because of unexpected urls, inaccessible links, and so forth
2. Use URI to represent each url, which at the moment, is just a String. The benefit of using URI is to get more structured information about links and have better control of them
3. Internal links, external links, and images are currently represented as String. A better OOP approach could be using the composite design pattern. For example, create an interface call Resource. Internal link / external link / image can implement this interface
4. A better sitemap, e.g. in xml format
