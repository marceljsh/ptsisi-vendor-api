# Vendor Management API

Build for PT Sinergi Informatika Semen Indonesia (SISI) by **[Achmad Fadillah](https://marjos.vercel.app)**.

## `🚀 How to use`

You can use one of these accounts to login:
|           Email          |   Password   |
|--------------------------|--------------|
| tyrion@casterlyrock.com  | WineAndWit   |
| danaerys@dragonstone.com | FireAndBlood |

Or you can register a new account, see [here](#endpoint-auth).

> [!CAUTION]
> Please configure `.env` file before you start.


## `📦 Endpoints`

Every single failed request will have response body like this:

```json
{
    "success": false,
    "data": {
        "message": "<some-error-message>"
    }
}
```

### Auth

<details>
  <a name="endpoint-register"></a>
  <summary><code>POST</code><code><b>/api/v1/sign-up</b></code></summary>

Register a new account.

#### Request Body

```json
{
    "email": "eddard@winterfell.com",
    "password": "WinterIsComing"
}
```

#### Responses

| http code | content-type       | response    |
| --------- | ------------------ | ----------- |
| `201`     | `application/json` | JSON string |
| `400`     | `application/json` | JSON string |

##### Response Body (201)

```json
{
    "success": true,
    "data": {
        "token": "<your-jwt-token>"
    }
}
```

#### Example cURL

```bash
curl "localhost:8080/api/v1/auth/sign-up" \
    -X POST \
    -H "Content-Type: application/json" \
    -d @payload.json
```

</details>

<details>
  <summary><code>POST</code><code><b>/api/v1/sign-in</b></code></summary>

Login to application.

#### Request Body

```json
{
    "email": "eddard@winterfell.com",
    "password": "WinterIsComing"
}
```

#### Responses

| http code | content-type       | response    |
| --------- | ------------------ | ----------- |
| `200`     | `application/json` | JSON string |
| `400`     | `application/json` | JSON string |

##### Response Body (200)

```json
{
    "success": true,
    "data": {
        "token": "<your-jwt-token>"
    }
}
```

#### Example cURL

```bash
curl "localhost:8080/api/v1/auth/sign-in" \
    -X POST \
    -H "Content-Type: application/json" \
    -d @payload.json
```

</details>

<details>
  <summary><code>POST</code><code><b>/api/v1/sign-out</b></code></summary>

Logout from application.

#### Responses

| http code | content-type               | response |
| --------- | -------------------------- | -------- |
| `200`     | `text/plain;charset=UTF-8` | None     |

#### Example cURL

```bash
curl "localhost:8080/api/v1/auth/register" \
    -X POST \
    -H "Authorization: Bearer <your-api-token>"
```

</details>

### Vendor

<details>
  <summary><code>GET</code><code><b>/api/v1/vendors?name={}&location={}&active-only={}&page={}&size={}</b></code></summary>

Gets vendors where by default page is 0 and size is 10


#### Query Parameters

| name          | type     | data type | description                      |
| ------------- | -------- | --------- | -------------------------------- |
| `name`        | optional | string    | keyword for name                 |
| `location`    | optional | string    | keyword for location             |
| `active-only` | optional | string    | whether vendor is deleted or not |
| `page`        | optional | integer   | page index                       |
| `size`        | optional | integer   | max number of items in a page    |

#### Responses

| http code | content-type       | response    |
| --------- | ------------------ | ----------- |
| `200`     | `application/json` | JSON string |

##### Response Body (200)

```json
{
    "success": true,
    "data": null
}
```

#### Example cURL

```bash
curl "localhost:8080/api/v1/vendors?name=sigmaka&location=Jakarta&active=true&page=0&size=5" \
    -X GET \
    -H "Authorization: Bearer <your-api-token>"
```

</details>

<details>
  <summary><code>GET</code><code><b>/api/v1/vendors/:id</b></code></summary>

Gets a vendor by id.

#### Path Parameters

| name | type     | data type | description             |
| ---- | -------- | --------- | ----------------------- |
| `id` | required | integer   | uuid of vendor instance |

#### Responses

| http code | content-type               | response    |
| --------- | -------------------------- | ----------- |
| `200`     | `application/json`         | JSON string |
| `404`     | `text/plain;charset=UTF-8` | JSON string |

##### Response Body (200)

```json
{
    "success": true,
    "data": null
}
```

##### Response Body (404)

```json
{
    "success": false,
    "data": {
        "message": "vendor not found"
    }
}
```

#### Example cURL

```bash
curl "localhost:8080/api/v1/vendors?name=sigmaka&location=Jakarta&active=true&page=0&size=5" \
    -X GET \
    -H "Authorization: Bearer <your-api-token>"
```

</details>

<details>
  <summary><code>POST</code><code><b>/api/v1/vendors</b></code></summary>

Creates a vendor.

#### Request Body

```json
{
    "name": "PT Pro Sigmaka Mandiri",
    "location": "Mampang Prapatan, Jakarta Selatan"
}
```

#### Responses

| http code | content-type       | response    |
| --------- | ------------------ | ----------- |
| `201`     | `application/json` | JSON string |
| `400`     | `application/json` | JSON string |

##### Response Body (201)

```json
{
    "success": true,
    "data": {
        "vendor": {
            "id": "ce882dbb-54c6-4cd3-984e-52656ae29ed8",
            "name": "PT Pro Sigmaka Mandiri",
            "location": "Mampang Prapatan, Jakarta Selatan",
            "created_at": "2024-11-18T06:04:53.251034+00:00",
            "updated_at": "2024-11-18T06:04:53.251034+00:00",
            "deleted_at": null,
        }
    }
}
```

#### Example cURL

```bash
curl "localhost:8080/api/v1/vendors" \
    -X POST \
    -H "Authorization: Bearer <your-api-token>" \
    -H "Content-Type: application/json" \
    -d @payload.json
```

</details>

<details>
  <summary><code>PUT</code><code><b>/api/v1/vendors/:id</b></code></summary>

Updates an existing vendor.

#### Path Parameters

| name | type     | data type | description             |
| ---- | -------- | --------- | ----------------------- |
| `id` | required | integer   | uuid of vendor instance |

#### Request Body

```json
{
    "name": "PT Pro Sigmaka Mandiri",
    "location": "Kecamatan Mampang Prapatan, Jakarta Selatan"
}
```

#### Responses

| http code | content-type       | response    |
| --------- | ------------------ | ----------- |
| `200`     | `application/json` | JSON string |
| `400`     | `application/json` | JSON string |

##### Response Body (200)

```json
{
    "success": true,
    "data": {
        "vendor": {
            "id": "ce882dbb-54c6-4cd3-984e-52656ae29ed8",
            "name": "PT Pro Sigmaka Mandiri",
            "location": "Kecamatan Mampang Prapatan, Jakarta Selatan",
            "created_at": "2024-11-18T06:04:53.251034+00:00",
            "updated_at": "2024-11-18T06:05:14.340089+00:00",
            "deleted_at": null,
        }
    }
}
```

#### Example cURL

```bash
curl "localhost:8080/api/v1/vendors/ce882dbb-54c6-4cd3-984e-52656ae29ed8" \
    -X PUT \
    -H "Authorization: Bearer <your-api-token>" \
    -H "Content-Type: application/json" \
    -d @payload.json
```

</details>

<details>
  <summary><code>DELETE</code><code><b>/api/v1/vendors/:id</b></code></summary>

Deletes an existing vendor.

#### Path Parameters

| name | type     | data type | description             |
| ---- | -------- | --------- | ----------------------- |
| `id` | required | integer   | uuid of vendor instance |

#### Responses

| http code | content-type       | response    |
| --------- | ------------------ | ----------- |
| `200`     | `application/json` | JSON string |
| `400`     | `application/json` | JSON string |

##### Response Body (200)

```json
{
    "success": true,
    "data": {
        "id": "ce882dbb-54c6-4cd3-984e-52656ae29ed8"
    }
}
```

#### Example cURL

```bash
curl "localhost:8080/api/v1/vendors/ce882dbb-54c6-4cd3-984e-52656ae29ed8" \
    -X DELETE \
    -H "Authorization: Bearer <your-api-token>"
```
</details>

---
