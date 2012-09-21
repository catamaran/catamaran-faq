WEBAPP=catamaran-faq

# First remove old symlinks
rm target/$WEBAPP/WEB-INF/freemarker
rm target/$WEBAPP/css
rm target/$WEBAPP/js
rm target/$WEBAPP/img

# Then clean
mvn clean

