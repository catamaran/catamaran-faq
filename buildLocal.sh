# Perform a maven webapp build BUT replace static web content with symlinks to the source SO THAT local file changes appear immediately

WEBAPP=catamaran-faq

# First remove old symlinks
rm target/$WEBAPP/WEB-INF/freemarker
rm target/$WEBAPP/css
rm target/$WEBAPP/js
rm target/$WEBAPP/img

# Then build which will move files from src/ to target/
mvn compile war:exploded

# Remove newly copied static files from target/
rm -r target/$WEBAPP/WEB-INF/freemarker
rm -r target/$WEBAPP/css
rm -r target/$WEBAPP/js
rm -r target/$WEBAPP/img

# Re-create the symlinks
pushd .
cd target/$WEBAPP/WEB-INF/
ln -s ../../../src/main/webapp/WEB-INF/freemarker/ freemarker
cd ..
ln -s ../../src/main/webapp/css/ css
ln -s ../../src/main/webapp/js/ js
ln -s ../../src/main/webapp/img img
popd
