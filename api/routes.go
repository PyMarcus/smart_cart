package main

import (
	"encoding/json"
	"log"
	"net/http"
	"strings"
	"time"
	"sync"
	"github.com/gorilla/mux"
	"github.com/gorilla/websocket"
)

var mutex sync.RWMutex

//key: cart identifier
//value: product_name+price
var products = make(map[string][]Product)

func getProducts(w http.ResponseWriter, r *http.Request){
	param := mux.Vars(r)
	
	for cart, product := range products{
		if cart == param["id"]{
			json.NewEncoder(w).Encode(product)
			return
		}
	}
	getDataFromWebsocket(param["id"], w)
}

// data to app
func getDataFromWebsocket(id string, w http.ResponseWriter){
	  ws := "ws://localhost:6666/"
	  log.Println(ws)
	  conn, _, err := websocket.DefaultDialer.Dial(ws, nil)
	  if err != nil{
	    log.Fatal(err)
	  }
	  msg := &WSMessage{Topic: "topico", Data: "ping"}	  
	  log.Println("Sending message ", msg)
	  data, err := json.Marshal(msg)
	  if err != nil{
	     log.Println("Fail to marshal message")
	     log.Fatal(err)
	  }
	  	  
	  go func() {
	  
		for {
		    time.Sleep(1)
			conn.WriteMessage(websocket.TextMessage, data)
			time.Sleep(1)

			_, message, err := conn.ReadMessage()

			if err != nil {
				log.Println("Error reading message:", err)
				mutex.Unlock()
				return
			}
			
			// if customer has been disconnected, so, the cart will be removed
			if websocket.IsUnexpectedCloseError(err, websocket.CloseGoingAway, websocket.CloseAbnormalClosure) {
				log.Printf("WebSocket closed: %v", err)
				mutex.Unlock()
				delete(products, id)
				return
			}
			
			messagePart := strings.Split(string(message), " ")
			log.Println("Product and price ", messagePart)
			log.Println(len(messagePart))

			if len(messagePart) >= 2{
				product := &Product{
					Name: strings.Replace( messagePart[0], "[", "", -1),
					Price: strings.Replace(messagePart[1], "]", "", -1),
				}
				mutex.Lock()
				products[id] = append(products[id], *product)
				mutex.Unlock()

				log.Printf("Sending product: %s with cost: %s\n", product.Name, product.Price)
				
				json.NewEncoder(w).Encode(products[id])
			}
			
		}
	}()

}

func Router() *mux.Router{
    route := mux.NewRouter()
    
    route.HandleFunc("/products/{id}", getProducts).Methods("GET")    
	return route
}