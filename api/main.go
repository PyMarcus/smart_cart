package main

import (
	"fmt"
	"log"
	"net/http"
)

func main(){
	port := 8080
	log.Println("Running on port ", port)
	router := Router()
	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%d", port), router))
}