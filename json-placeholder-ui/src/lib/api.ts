import axios from 'axios'

const BASE_URL = 'https://jsonplaceholder.typicode.com'

export const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
})

export interface Geo {
  lat: string
  lng: string
}

export interface Address {
  street: string
  suite: string
  city: string
  zipcode: string
  geo: Geo
}

export interface Company {
  name: string
  catchPhrase: string
  bs: string
}

export interface User {
  id: number
  name: string
  username: string
  email: string
  address: Address
  phone: string
  website: string
  company: Company
}

export const getPosts = async (): Promise<Post[]> => {
  const { data } = await api.get<Post[]>('/posts')
  return data
}

export const getUsers = async (): Promise<User[]> => {
  const { data } = await api.get<User[]>('/users')
  return data
}

export const getPost = async (id: number): Promise<Post> => {
  const { data } = await api.get<Post>(`/posts/${id}`)
  return data
}

export const getUser = async (id: number): Promise<User> => {
  const { data } = await api.get<User>(`/users/${id}`)
  return data
} 