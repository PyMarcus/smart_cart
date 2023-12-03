package main 

type WSMessage struct{
	Data string  `json:"data"`
	Topic     string  `json:"topic"`
}