package contracts

import org.springframework.cloud.contract.spec.Contract

/* groovylint-disable-next-line CompileStatic */
Contract.make {
    description 'obter cotacao de preco do bitcoin'
    request {
        method GET()
        url('/BTC-USD')
        headers {
            contentType(applicationJson())
        }
    }
    response {
        status 200
        body([
             code: "BTC",
             codein: "USD",
             name: "Bitcoin/Dólar Americano",
             high: "23.435",
             low: "22.836",
             varBid: "247",
             pctChange: "1.08",
             bid: "23.17",
             ask: "23.179",
             timestamp: "1659916718",
             create_date: "2022-08-07 20:58:38"
        ])
        headers {
            contentType(applicationJson())
        }
    }
}