<!DOCTYPE html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">
  <h1 color="blue">Sign-in</h1>
    <#if userTaken??>
        <p>
          ${userTaken}
        </p>
    </#if>
  <div class="sign-in">
    <form action="./signin" method="POST">
      <br/>
      <input name="username" />
      <br/>
      <br/>
      <button type="submit">Sign-in</button>
    </form>
  </div>
</div>
</body>

</hmtl>