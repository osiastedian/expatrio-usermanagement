<html>
<head>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>

<div class="container d-flex flex-column h-100 justify-content-center">
    <form class="mx-auto w-25" method="post" action="/login">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="email" name="username" class="form-control">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" name="password" class="form-control">
        </div>
        <input type="hidden" name="client-id" value="cli">
        <button type="submit" class="btn btn-primary">Login</button>
    </form>
</div>
</body>
</html>