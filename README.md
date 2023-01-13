<h3>URLs da API</h3>

<p>
    Listar todas as pessoas: GET ->
    <a href="http://localhost:8080/api/persons">http://localhost:8080/api/persons</a>
</p>
<p>
    Salvar pessoa: POST ->
    <a href="http://localhost:8080/api/persons">http://localhost:8080/api/persons</a>
    <br/>
    <code>{
        "name": "Indiana Jones",
        "birthDate": "1994-06-20"
}</code>
</p>
<p>
    Procurar por id: GET ->
    <a href="http://localhost:8080/api/persons/1">http://localhost:8080/api/persons/1</a><br/>
</p>
<p>
    Atualizar pessoa: PUT ->
    <a href="http://localhost:8080/api/persons/1">http://localhost:8080/api/persons/1</a><br/>
    <code>{
        "name": "Juju"
}</code>
</p>
<p>
    Adicionar endereço a uma pessoa: POST ->
    <a href="http://localhost:8080/api/address/1">http://localhost:8080/api/address/1</a><br/>
    <code>{
        "publicPlace": "Pelourinho",
        "zipCode": "998899",
        "number": "748",
        "city": "Salvador"
}</code>
</p>
<p>
    Listar endereços de uma pessoa: GET ->
    <a href="http://localhost:8080/api/address/1">http://localhost:8080/api/address/1</a><br/>
</p>

<p>
    Procurar endereço principal de uma pessoa: GET ->
    <a href="http://localhost:8080/api/address/1/main">http://localhost:8080/api/address/1/main</a><br/>
</p>

<p>
    Mudar o endereço principal de uma pessoa: PUT ->
    <a href="http://localhost:8080/api/address/1/main/2">http://localhost:8080/api/address/1/main/2</a><br/>
</p>
