html: README.html
pdf: README.pdf

clean:
	rm -f README.pdf README.html

README.html: README.txt
	asciidoc -a icons README.txt

README.pdf: README.txt
	a2x --verbose -f pdf --dblatex-opts='-T db2latex -I images/icons' README.txt
