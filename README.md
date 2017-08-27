# autobot - PDF parsing and extraction utility using Apache Tika

Autobot parses the PDF files using Apache Tika and extracts the title, authorString and contents of the IEEE Xplore PDFs.


Please download the utility jar from the link below:
https://github.com/sidmishraw/autobot/blob/master/build/libs/autobot-1.0.0.jar

Description:

It requires 2 inputs:

1>  Absolute file-path of a file named “conf.txt”

	This file will have the list of all file-paths of the input PDF documents on each line

For eg:

```conf.txt
path-to-pdfs\04403110.pdf
path-to-pdfs\04403128.pdf
path-to-pdfs\04403127.pdf
```
2> Absolute file-path of the output directory.



Usage:

java -jar autobot-1.0.0.jar “path-to-conf.txt” “path-to-output-directory”.

For eg: 
```
java -jar autobot-1.0.0.jar "/Users/sidmishraw/Downloads/conf.txt" "/Users/sidmishraw/Downloads/outpdfs"
```


Caveats:

• It cannot get the exact author names, but I’ve made it to extract and group together the author name area string together and it is named “authorString”.

```javascript
{
  "title": "Incompleteness Errors in Ontology",
  "authorString": [
    "1 Muhammad Abdul Qadir, 2Muhammad Fahad, 3Syed Adnan Hussain Shah Muhammad Ali Jinnah University, Islamabad, Pakistan",
    "1aqadir@jinnah.edu.pk, 2mhd.fahad@gmail.com, 3syedadnan@gmail.com"
  ],
  "content": "Abstract\nOntology ev…”
}
```

As you can see from the example, if there are numbered bullets in-front of the name’s etc, it is still difficult to remove them.

Some, PDF documents turn out good:

```javascript
{
  "title": "Privacy Preserving Collaborative Filtering using Data Obfuscation",
  "authorString": [
    "Rupa Parameswaran Georgia Institute of Technology",
    "School of Electrical and Computer Engineering Atlanta, GA",
    "rupa@ece.gatech.edu",
    "Douglas M Blough Georgia Institute of Technology",
    "School of Electrical and Computer Engineering Atlanta, GA",
    "doug.blough@ece.gatech.edu"
  ],
  "content": "Abstract\n…”
}
```
