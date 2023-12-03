package main 

type Product struct{
   Name string    `json:"name"`
   Price string   `json:"price"`
}

func NewProduct(name, price string) *Product{
   return &Product{
      Name: name,
      Price: price,
   }
}