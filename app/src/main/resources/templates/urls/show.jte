@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.util.NamedRoutes
@param UrlPage page

@template.layout.page(
page = page,
content = @`
    <main class="flex-grow-1">
        <section>
            <div class="container-lg mt-5">
                <h1>Сайт: ${page.getUrl().getName()}</h1>
                <table class="table table-bordered table-hover mt-3">
                    <tr>
                        <td>Id</td>
                        <td>${page.getUrl().getId()}</td>
                    </tr>
                    <tr>
                        <td>Имя</td>
                        <td>${page.getUrl().getName()}</td>
                    </tr>
                    <tr>
                        <td>Дата создания</td>
                        <td>${page.getUrl().getCreatedAt().toString()}</td>
                    </tr>
                </table>
            </div>
            <div class="container-lg mt-5">
                <h2>Проверки</h2>
                <form method="post" action="${NamedRoutes.urlCheckPath(page.getUrl().getId())}">
                    <button type="submit" class="btn btn-primary">Запустить проверку</button>
                </form>
                <table class="table table-bordered table-hover mt-3">
                    <tbody>
                    <tr>
                        <th>ID</th>
                        <th>Код ответа</th>
                        <th>title</th>
                        <th>h1</th>
                        <th>description</th>
                        <th>Дата проверки</th>
                    </tr>
                    </tbody>
                    @if(!page.getUrlList().isEmpty())
                    @for(var check : page.getUrlList())
                        <tr>
                            <td>${check.getId()}</td>
                            <td>${check.getStatusCode()}</td>
                            <td>${check.getTitle()}</td>
                            <td>${check.getH1()}</td>
                            <td>${check.getDescription()}</td>
                            <td>${check.getCreatedAt().toString()}</td>
                        </tr>
                    @endfor
                    @endif

                </table>
            </div>
        </section>
    </main>
`
)