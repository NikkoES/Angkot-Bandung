<?php

use Slim\Http\Request;
use Slim\Http\Response;
use Slim\Http\UploadedFile;

// Routes

$app->get('/[{name}]', function (Request $request, Response $response, array $args) {
    // Sample log message
    $this->logger->info("Slim-Skeleton '/' route");

    // Render index view
    return $this->renderer->render($response, 'index.phtml', $args);
});

$app->get("/angkots/", function (Request $request, Response $response){
    $sql = "SELECT * FROM data_angkot";
    $stmt = $this->db->prepare($sql);
    $stmt->execute();
    $result = $stmt->fetchAll();
    return $response->withJson(["status" => "success", "data" => $result], 200);
});

$app->post("/angkots/", function (Request $request, Response $response){

    $new_angkot = $request->getParsedBody();

    $sql = "INSERT INTO data_angkot (kode, trayek, jarak, jumlah) VALUE (:kode, :trayek, :jarak, :jumlah)";
    $stmt = $this->db->prepare($sql);

    $data = [
    	":kode" => $new_angkot["kode"],
        ":trayek" => $new_angkot["trayek"],
        ":jarak" => $new_angkot["jarak"],
        ":jumlah" => $new_angkot["jumlah"]
    ];

    if($stmt->execute($data))
       return $response->withJson(["status" => "success", "data" => "1"], 200);
    
    return $response->withJson(["status" => "failed", "data" => "0"], 200);
});


$app->post('/angkots/angkot/{kode}', function(Request $request, Response $response, $args) {
    
    $uploadedFiles = $request->getUploadedFiles();
    
    // handle single input with single file upload
    $uploadedFile = $uploadedFiles['gambar'];
    if ($uploadedFile->getError() === UPLOAD_ERR_OK) {
        
        $extension = pathinfo($uploadedFile->getClientFilename(), PATHINFO_EXTENSION);
        
        $filename = sprintf('%s.%0.8s', $args["kode"], $extension);
        
        $directory = $this->get('settings')['upload_directory'];
        $uploadedFile->moveTo($directory . DIRECTORY_SEPARATOR . $filename);

        // simpan nama file ke database
        $sql = "UPDATE data_angkot SET gambar=:gambar WHERE kode=:kode";
        $stmt = $this->db->prepare($sql);
        $params = [
            ":kode" => $args["kode"],
            ":gambar" => $filename
        ];
        
        if($stmt->execute($params)){
            // ambil base url dan gabungkan dengan file name untuk membentuk URL file
            $url = $request->getUri()->getBaseUrl()."/picture/".$filename;
            return $response->withJson(["status" => "success", "data" => $url], 200);
        }
        
        return $response->withJson(["status" => "failed", "data" => "0"], 200);
    }
});
