for threads in 050 100 150 200 250 300 350 400 450 500 550 600 650 700 750 800; do
	java -jar load-generator-1.0-jar-with-dependencies.jar $threads 2500 > test.log 2>&1
	mv shipment.log lowest_price.log purchase.log data/$1/time/$threads/
done
