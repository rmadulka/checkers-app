<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

  </div>

  <!-- create a <ul> for players online -->
<div class = "players">
  <#if playersOnline??>
      <ul>
        <#list playersOnline as p>
            <li>${p}</li>
        </#list>
      </ul>
  </#if>
</div>
<#--  <div class="players">-->
<#--    <h2> Players Online</h2>-->
<#--    <ul>-->
<#--      ${playersOnline}-->
<#--    </ul>-->

<#--  </div>-->

</div>
</body>

</html>
