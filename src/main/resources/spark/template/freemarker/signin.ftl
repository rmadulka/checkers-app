<!DOCTYPE html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">
    <#if userTaken??>
        <p>
          ${userTaken}
        </p>
    </#if>
  <form action="./signin" method="POST">

    <br/>
    <input name="Username" />
    <br/>
    <br/>
    <button type="submit">Ok</button>
  </form>

</div>
</body>

</hmtl>