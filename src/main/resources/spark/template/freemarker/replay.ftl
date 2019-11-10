<!DOCTYPE html>

<head>
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <h4>Replays</h4>
    <#--<ul>-->
      <#--<#list games as g>-->
          <#--<li>-->
            <#--<form id="form" action="./replay/game" method="GET">-->
              <#--<input type="hidden" name="receiver" value="${g.name}" />-->
              <#--<a href="javascript:;" onclick="document.getElementById('form').submit();"> [${g.name}] </a>-->
            <#--</form>-->
          <#--</li>-->
      <#--</#list>-->
    <#--</ul>-->

  </div>
</div>
</body>

</html>

