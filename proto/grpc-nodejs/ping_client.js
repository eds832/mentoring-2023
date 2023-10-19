const PROTO_PATH = "./ping.proto";
const grpc = require('@grpc/grpc-js');
const protoLoader = require('@grpc/proto-loader');
const packageDefinition = protoLoader.loadSync(
    PROTO_PATH,
    {keepCase: true,
     longs: String,
     enums: String,
     defaults: true,
     oneofs: true
    });

const ping = grpc.loadPackageDefinition(packageDefinition).com.epam.stubs.message;

const client = new ping.PingService(
  'localhost:8080',
  grpc.credentials.createInsecure()
);

function call(ms) {	
	client.getPong({message: ms}, function(err, response) {
		console.log('Request:', {message: ms});
		console.log('Response:', response);
	});
}

call('Incorrect');
call('Ping');

