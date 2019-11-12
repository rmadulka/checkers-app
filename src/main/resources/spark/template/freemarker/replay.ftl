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
    <div class="Games">
      <#if games??>
        <ul>
          <#list games as g>
              <li>
                <form id="game${g.id}" action="./replay/game" method="GET">
                  <input type="hidden" name="gameID" value="${g.id}" />
                  <a href="javascript:;" onclick="document.getElementById('game${g.id}').submit();"> ${g.redPlayer.name}[Red] vs ${g.whitePlayer.name}[White] - ${g.endDate} </a>
                </form>
              </li>
          </#list>
        </ul>
      </#if>
    </div>

  </div>
</div>
</body>

</html>

